package yueworld.PVUV;


import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.common.state.StateTtlConfig;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.calcite.shaded.com.google.common.hash.BloomFilter;
import org.apache.flink.calcite.shaded.com.google.common.hash.Funnels;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.StateBackend;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.functions.timestamps.AscendingTimestampExtractor;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.evictors.TimeEvictor;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.ContinuousEventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.CountTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import yueworld.sink.MyJdbcSink;
import yueworld.sink.MyRedisSink;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Properties;

/**
 * 计算当天的pv uv 00:00:00  ~  24:00:00 ,清空昨天的数据
 *  结果 实时写入reids
 */
// yueworld.PVUV.MyPvUv
public class MyPvUv {

    public static final DateTimeFormatter TIME_FORMAT_YYYY_MM_DD_HHMMSS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception {

        // 获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置快照时间间隔和恰一次处理语义 只有开启了CheckPointing,才会有重启策略
        env.enableCheckpointing(5000, CheckpointingMode.EXACTLY_ONCE);

        // 生产设置checkpoint目录
        //env.setStateBackend(new FsStateBackend("hdfs://nameservice1/flink_pvuv/checkpoint"));

        // 本地测试checkpoint目录
        env.setStateBackend(new FsStateBackend("file:///C://Users//ghw20//Desktop//代码//flink_java//src//main//resources//CK"));

        // checkpoint的清除策略 系统异常退出或人为 Cancel 掉，不删除checkpoint数据
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // 设置重启策略 5次尝试 每次尝试间隔50s
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(5,50000));

        // 读取配置文件
        //String path = MyPvUv.class.getClassLoader().getResource("myConf.properties").getPath();

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
        DataStream<String> detailStream = env.addSource(detailLog).name("uv-pv_log").disableChaining();
        detailStream.print();
        DataStream<Tuple2<UMessage,Integer>> detail = detailStream.map(new MyMapFunction())
                .filter(s->s!=null&&s.f0!=null)
                // 第一重数据延迟保障 5s
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<Tuple2<UMessage, Integer>>(Time.seconds(5)) {
                    @Override
                    public long extractTimestamp(Tuple2<UMessage, Integer> element) {
                        LocalDate localDate=LocalDate.parse(element.f0.getCreateTime(),TIME_FORMAT_YYYY_MM_DD_HHMMSS);
                        long timestamp = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                        return timestamp;
                    }
                });

        DataStream<Tuple3<String, Integer, Integer>> statsResult = detail.keyBy(new MyKeySelectorFunction())
                .window(TumblingEventTimeWindows.of(Time.days(1), Time.hours(-8)))
                // .trigger(ContinuousEventTimeTrigger.of(Time.seconds(10)))  // 虽然因为窗口大小是一天，不可能等到一天过去后才看到计算记过，所以这里可以根据事件时间间隔设置每10秒触发一次计算
                .trigger(CountTrigger.of(1)) // 每来一条数据触发一次计算
                .allowedLateness(Time.seconds(10)) // 第二重数据延迟保障 10s
                .evictor(TimeEvictor.of(Time.seconds(0), true)) // 剔除数据
                .process(new MyProcessWindowFunction());

        // pv uv 数据写入redis
        //statsResult.addSink(new MyRedisSink());

        // pv uv 数据写入mysql
        statsResult.addSink(new MyJdbcSink());

        env.execute("pv-uv1");
    }


    /**
     *  自定义mapFunction
     */

    public static class MyMapFunction implements MapFunction<String, Tuple2<UMessage,Integer>>{

        @Override
        public Tuple2<UMessage, Integer> map(String value) throws Exception {
            try {
                UMessage uMessage = JSON.parseObject(value, UMessage.class);
                return Tuple2.of(uMessage,1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Tuple2.of(null,null);
        }
    }


    /**
     * 自定义 KeySelector
     */
    public static class MyKeySelectorFunction implements KeySelector<Tuple2<UMessage, Integer>, String>{

        @Override
        public String getKey(Tuple2<UMessage, Integer> value) throws Exception {
            // 按yyyy-mm-dd 分组
            return value.f0.getCreateTime().substring(0, 10);
        }
    }

    /**
     * 自定义ProcessWindowFunction
     */
    public static class MyProcessWindowFunction extends ProcessWindowFunction<Tuple2<UMessage, Integer>, Tuple3<String, Integer, Integer>, String, TimeWindow> {

        //使用 KeyedState(用于任务失败重启后，从State中恢复数据)

        // 定义状态
        private transient ValueState<BloomFilter<String>> boomFilterState;
        private transient ValueState<Integer> uvCountState;
        private transient ValueState<Integer> pvCountState;

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            // 配置状态过期时间
            StateTtlConfig ttlConfig = StateTtlConfig.newBuilder(org.apache.flink.api.common.time.Time.minutes(60 * 6))
                    .setUpdateType(StateTtlConfig.UpdateType.OnCreateAndWrite)
                    .setStateVisibility(StateTtlConfig.StateVisibility.NeverReturnExpired)
                    .build();
            //定义一个状态描述器[BloomFilter]
            ValueStateDescriptor<BloomFilter<String>> boomFilterDescriptor = new ValueStateDescriptor<BloomFilter<String>>("boom_filter", TypeInformation.of(new TypeHint<BloomFilter<String>>() {
            }));
            //定义一个状态描述器[pv],[uv]
            ValueStateDescriptor<Integer> pvDescriptor = new ValueStateDescriptor<Integer>("pv_count", Integer.class);
            ValueStateDescriptor<Integer> uvDescriptor = new ValueStateDescriptor<Integer>("uv_count", Integer.class);
            // 开启状态过期机制
            boomFilterDescriptor.enableTimeToLive(ttlConfig);
            pvDescriptor.enableTimeToLive(ttlConfig);
            uvDescriptor.enableTimeToLive(ttlConfig);
            //使用RuntimeContext获取状态
            boomFilterState = getRuntimeContext().getState(boomFilterDescriptor);
            pvCountState = getRuntimeContext().getState(pvDescriptor);
            uvCountState = getRuntimeContext().getState(uvDescriptor);
        }

        @Override
        public void process(String key, Context context, Iterable<Tuple2<UMessage, Integer>> elements, Collector<Tuple3<String, Integer, Integer>> out) throws Exception {

            // 从状态中获取pv,uv
            Integer uv = uvCountState.value();
            Integer pv = pvCountState.value();
            BloomFilter<String> bloomFilter = boomFilterState.value();
            // 如果bloomFilter为空 初始化一个bloomFilter
            if (bloomFilter == null) {
                bloomFilter = BloomFilter.create(Funnels.unencodedCharsFunnel(), 10*1000*1000L);
                uv = 0;
                pv = 0;
            }

            Iterator<Tuple2<UMessage, Integer>> mapIterator = elements.iterator();
            while (mapIterator.hasNext()) {
                pv += 1;
                UMessage uMessage = mapIterator.next().f0;
                String uid = uMessage.getUid();
                if (!bloomFilter.mightContain(uid)) {
                    bloomFilter.put(uid); //不包含就添加进去
                    uv += 1;
                }
            }

            // 更新状态
            boomFilterState.update(bloomFilter);
            uvCountState.update(uv);
            pvCountState.update(pv);

//            out.collect(Tuple3.of(key, "uv", uv));
//            out.collect(Tuple3.of(key, "pv", pv));
            // pv  uv
            out.collect(Tuple3.of(key,pv,uv));
        }
    }
}
