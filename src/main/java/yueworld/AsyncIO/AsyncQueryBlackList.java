package yueworld.AsyncIO;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import net.sf.json.JSONObject;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import yueworld.PVUV.MyPvUv;
import yueworld.eventSchema.KafkaEventSchema;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * TODO 读取 Kafka 数据,异步查询mysql,从kafka中过滤出黑名单信息
 *
 * 数据格式：{"id":1,"createTime":"2020-06-05 14:03:16","name":"A001",age:34,"address":"B001"}
 */

public class AsyncQueryBlackList {
    public static void main(String[] args) throws Exception {
        // 获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置快照时间间隔和恰一次处理语义 只有开启了CheckPointing,才会有重启策略
        env.enableCheckpointing(5000, CheckpointingMode.EXACTLY_ONCE);

        // 设置checkpoint目录
        env.setStateBackend(new FsStateBackend("hdfs://nameservice1/AsyncQueryBlackList/checkpoint"));

        // checkpoint的清除策略 系统异常退出或人为 Cancel 掉，不删除checkpoint数据
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // 设置重启策略 5次尝试 每次尝试间隔50s
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5,50000));

        // 读取配置文件
        ParameterTool parameters = ParameterTool.fromPropertiesFile(MyPvUv.class.getResourceAsStream("/myBlackListConf.properties"));
        //设置全局参数
        env.getConfig().setGlobalJobParameters(parameters);

        // kafka配置信息
        Properties propsConsumer = new Properties();
        propsConsumer.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, parameters.getRequired("bootstrap.server"));
        propsConsumer.put(ConsumerConfig.GROUP_ID_CONFIG, parameters.getRequired("group.id"));
        //Kafka的消费者，不自动提交偏移量
        propsConsumer.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        //如果没有记录偏移量，第一次从最开始消费
        propsConsumer.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        FlinkKafkaConsumer<JSONObject> detailLog = new FlinkKafkaConsumer<JSONObject>(parameters.getRequired("topics"), new KafkaEventSchema(), propsConsumer);
        DataStream<JSONObject> source = env.addSource(detailLog).name("blackList_log").disableChaining();
        //source.print();

        // add async operator to streaming job
        DataStream<JSONObject> result;
        if (false) {
            result = AsyncDataStream.orderedWait(
                    source,
                    new SampleAsyncFunction(),
                    1000000L,
                    TimeUnit.MILLISECONDS,
                    20).setParallelism(1);
        }
        else {
            result = AsyncDataStream.unorderedWait(
                    source,
                    new SampleAsyncFunction(),
                    10000,
                    TimeUnit.MILLISECONDS,
                    20).setParallelism(1);
        }

        result.printToErr();


        env.execute("blackListJob");

    }


    /**
     *  自定义异步IO查询Function
     */
    private static class SampleAsyncFunction extends RichAsyncFunction<JSONObject, JSONObject> {
        private transient SQLClient mySQLClient;
        private Cache<String, String> Cache;

        /**
         * 创建mysql查询客户端和缓存容器
         * @param parameters
         * @throws Exception
         */
        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            Cache = Caffeine
                    .newBuilder()
                    .maximumSize(1025) // 缓存大小 最大可以存储1025个记录
                    .expireAfterAccess(10, TimeUnit.MINUTES) // 过期时间 10分钟
                    .build();

            JsonObject mySQLClientConfig = new JsonObject();
            mySQLClientConfig.put("url", "jdbc:mysql://192.168.121.15:3306/test")
                    .put("driver_class", "com.mysql.jdbc.Driver")
                    .put("max_pool_size", 20)
                    .put("user", "root")
//                    .put("max_idle_time",1000)
                    .put("password", "yueworlddata");

            VertxOptions vo = new VertxOptions();
            vo.setEventLoopPoolSize(10);
            vo.setWorkerPoolSize(20);

            Vertx vertx = Vertx.vertx(vo);
            mySQLClient = JDBCClient.createNonShared(vertx, mySQLClientConfig);

        }

        /**
         * 关闭mysql查询客户端和清空缓存
         * @throws Exception
         */
        @Override
        public void close() throws Exception {
            super.close();
            if(mySQLClient!=null)
                mySQLClient.close();
            if(Cache!=null)
                Cache.cleanUp();
        }

        /**
         * 查询mysql
         * @param input
         * @param resultFuture
         */
        @Override
        public void asyncInvoke(final JSONObject input, final ResultFuture<JSONObject> resultFuture) {

            String key = input.getString("name");

            // 先从缓存中查找黑名单，如果缓存中没有再去数据库查找
            String cacheIfPresent = Cache.getIfPresent(key);
            if (cacheIfPresent != null) {
                input.put("type", "我是黑名单成员！");
                resultFuture.complete(Collections.singleton(input));
                return;
            }

            // 从数据库查找黑名单
            mySQLClient.getConnection(conn -> {
                if (conn.failed()) {
                    //Treatment failures
                    resultFuture.completeExceptionally(conn.cause());
                    return;
                }

                final SQLConnection connection = conn.result();
            /*
                结合自己的查询逻辑，拼凑出相应的sql，然后返回结果。
             */
                String querySql = "SELECT * FROM blackList where name = '" + key + "'";
                connection.query(querySql, res2 -> {
                    if(res2.failed()){
                        resultFuture.complete(null);
                        return;
                    }

                    if (res2.succeeded()) {
                        ResultSet rs = res2.result();
                        List<JsonObject> rows = rs.getRows();
                        if(rows.size() <= 0){
                            resultFuture.complete(Collections.EMPTY_LIST);
                            return;
                        }
                        for (JsonObject json : rows) {
                            // 获取mysql 数据
                            String type = json.getString("type");
                            String name = json.getString("name");
                            // 在黑名单信息后面拼接 黑名单类型
                            input.put("type", type);
                            // 将黑名单加入缓存
                            Cache.put(name,name);
                            resultFuture.complete(Collections.singleton(input));
                        }
                        // Do something with results
                    } else {
                        resultFuture.complete(null);
                    }
                });

                connection.close(done -> {
                    if (done.failed()) {
                        throw new RuntimeException(done.cause());
                    }
                });

            });
        }

    }
}
