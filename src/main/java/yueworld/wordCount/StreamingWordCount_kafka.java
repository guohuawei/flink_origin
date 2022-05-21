package yueworld.wordCount;


import net.sf.json.JSONObject;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import yueworld.eventSchema.KafkaEventSchema;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 *  flink 消费kafka 统计单词数量
 */
public class StreamingWordCount_kafka {
    public static void main(String[] args) throws Exception {
        // 获取执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置快照时间间隔和恰一次处理语义
        env.enableCheckpointing(20000, CheckpointingMode.EXACTLY_ONCE);
        // 设置checkpoint目录
//        env.setStateBackend(new FsStateBackend("/hdfs/checkpoint"));

        // checkpoint的清楚策略
        env.getCheckpointConfig()
                .enableExternalizedCheckpoints(CheckpointConfig.
                        ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        // 设置重启策略
        env.setRestartStrategy(RestartStrategies.
                fixedDelayRestart(5,//5次尝试
                        50000)); //每次尝试间隔50s
        env.getConfig().setAutoWatermarkInterval(1000); //1s

        // 设置事件时间
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // kafka配置信息
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.121.42:9092,192.168.121.43:9092,192.168.121.128:9092,192.168.121.192:9092,192.168.121.15:9092");
        properties.setProperty("group.id", "jsontest");

        FlinkKafkaConsumer<JSONObject> kafkaConsumer010 = new FlinkKafkaConsumer("jsontest",
                new KafkaEventSchema(), //自定义反序列化
                properties);
        // 从最新的offset开始消费消息
        kafkaConsumer010.setStartFromLatest();
        // 设置自定义时间戳分配器和watermark发射器，也可以在后面的算子中设置
        kafkaConsumer010.assignTimestampsAndWatermarks(new CustomWatermarkExtractor());

        env.addSource(kafkaConsumer010) // 添加数据源
           .keyBy(new KeySelector<JSONObject, String>() { // 注意 keyselector的使用
                    @Override
                    public String getKey(JSONObject value) throws Exception {
                        return value.getString("fruit");
                    }
                })
                .window(TumblingEventTimeWindows.of(Time.seconds(10))) // 滚动窗口，大小为10s
                .allowedLateness(Time.seconds(10)) // 允许10s延迟
                .reduce(new ReduceFunction<JSONObject>() {
                    public JSONObject reduce(JSONObject v1, JSONObject v2) {
                        String fruit = v1.getString("fruit");
                        int number = v1.getInt("number");
                        int number1 = v1.getInt("number");
                        int result = number1 +number;
                        JSONObject json = new JSONObject();
                        json.put("fruit",fruit);
                        json.put("number",result);
                        return json;
                    }
                }).print();

        env.execute("StreamingWordCount_kafka");

    }

    private static class CustomWatermarkExtractor implements AssignerWithPeriodicWatermarks<JSONObject> {

        private static final long serialVersionUID = -742759155861320823L;

        private long currentTimestamp = Long.MIN_VALUE;

        /**
         * @param event
         * @param previousElementTimestamp
         * @return
         */
        @Override
        public long extractTimestamp(JSONObject event, long previousElementTimestamp) {
            this.currentTimestamp = event.getLong("time");
            return this.currentTimestamp;
        }

        @Nullable
        @Override
        public Watermark getCurrentWatermark() {
            return new Watermark(currentTimestamp == Long.MIN_VALUE ? Long.MIN_VALUE : currentTimestamp - 1);
        }
    }
}
