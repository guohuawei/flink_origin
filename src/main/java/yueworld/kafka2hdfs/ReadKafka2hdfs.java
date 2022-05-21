package yueworld.kafka2hdfs;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import yueworld.POJO.BlackList;
import yueworld.PVUV.MyPvUv;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.flink.core.fs.Path;
import yueworld.jsonUtil.JsonUtil;

/**
 * 实时读取kafka数据写入hdfs  -DHADOOP_USER_NAME=root
 */
// yueworld.kafka2hdfs.ReadKafka2hdfs
public class ReadKafka2hdfs {

    public static void main(String[] args) throws Exception {

        // 获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置快照时间间隔和恰一次处理语义 只有开启了CheckPointing,才会有重启策略

        env.enableCheckpointing(5000, CheckpointingMode.EXACTLY_ONCE);

        // 生产设置checkpoint目录
        env.setStateBackend(new FsStateBackend("hdfs://nameservice1/flink_pvuv/checkpoint/ReadKafka2hdfs"));

        // 本地测试checkpoint目录
        //env.setStateBackend(new FsStateBackend("file:///C://Users//ghw20//Desktop//代码//flink_java//src//main//resources//ReadKafka2hdfs"));

        // checkpoint的清除策略 系统异常退出或人为 Cancel 掉，不删除checkpoint数据
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // 设置重启策略 5次尝试 每次尝试间隔50s
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5, 50000));

        ParameterTool parameters = ParameterTool.fromPropertiesFile(MyPvUv.class.getResourceAsStream("/myConf.properties"));
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

        FlinkKafkaConsumer<String> detailLog = new FlinkKafkaConsumer<String>(parameters.getRequired("topics"), new SimpleStringSchema(), propsConsumer);
        DataStream<String> detailStream = env.addSource(detailLog).name("blackListTopic_log").disableChaining();

        // 解析json字符串
        DataStream<String> mapStream = detailStream.map(new MapFunction<String, String>() {
            @Override
            public String map(String s) throws Exception {
                String obj = JsonUtil.getObject4JsonString(s, BlackList.class).toString();
                return obj;
            }
        });


        mapStream.print();
        // Flink的filesystem connector支持写入hdfs，同时支持基于Checkpoint的滚动策略，每次做Checkpoint时将inprogress的文件变为正式文件，可供下游读取。
        // --------------------写入hdfs----------------------------
        DefaultRollingPolicy<String, String> defaultRollingPolicy = DefaultRollingPolicy.builder()
                .withInactivityInterval(TimeUnit.SECONDS.toMillis(10))  ////10s未接收到数据，生成一个文件
                .withRolloverInterval(TimeUnit.SECONDS.toMillis(30)) //30s 生成一个文件
                .withMaxPartSize(1024 * 1024 * 1024) // 设置每个文件的最大大小 ,默认是128M。这里设置为1G,超过文件大小就写入新文件
                .build();

        StreamingFileSink<String> sink = StreamingFileSink
                .forRowFormat(new Path("hdfs://nameservice1/flink/blackListTopic"), new SimpleStringEncoder<String>("UTF-8"))//设置文件路径，以及文件中的编码格式
                .withBucketAssigner(new MyBucketAssigner())//设置自定义分桶
                .withRollingPolicy(defaultRollingPolicy)//设置文件滚动条件
                .withBucketCheckInterval(10000l)//设置检查点
                .build();

        mapStream.addSink(sink);
        env.execute(ReadKafka2hdfs.class.getSimpleName());

    }
}
