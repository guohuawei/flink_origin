package yueworld.waterMark;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import javax.annotation.Nullable;

/**
 *  窗口 + 水印  处理数据乱序问题
 */
public class WindowWaterMark {

    public static void main(String[] args) throws Exception {
        // 获取flink环境上下文
        final StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 设置为EvenTime事件类型
        environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        // 设置水印生成间隔 100ms
        environment.getConfig().setAutoWatermarkInterval(100);

        // 接收数据，并为每条数据设置水印
        DataStream<String> socketTextStream = environment.
                socketTextStream("192.168.121.15", 8888)
                .assignTimestampsAndWatermarks(new generateWaterMark());
        // 业务逻辑处理
        socketTextStream.map(new MyMapFunction())
                .keyBy(0)
                .window(TumblingEventTimeWindows.of(Time.seconds(5)))
                .minBy(1)
                .print();

        environment.execute(WindowWaterMark.class.getSimpleName());
    }

    public static class MyMapFunction implements MapFunction<String, Tuple2<String,Long>>{

        @Override
        public Tuple2<String, Long> map(String s) throws Exception {
            String[] split = s.split(",");
            Tuple2<String, Long> stringLongTuple2 = new Tuple2<>();
            stringLongTuple2.setFields(split[0],Long.parseLong(split[1]));
            return stringLongTuple2;
        }
    }

    //AssignerWithPeriodicWatermarks
    //周期性生成watermark（可能根据流中的元素，也可能根据处理时间）
    //该实现的 getCurrentWatermark 方法会被每隔一段时间调用一次，由 ExecutionConfig.setAutoWatermarkInterval 来定义
    //当返回的watermark不为空且比当前watermark大的话，该watermark会被使用
    public static class generateWaterMark implements AssignerWithPeriodicWatermarks<String>{

        // 当前最大时间戳
        private Long currentTimeStamp = 0L;
        //最大允许的乱序范围 5s
        private Long maxOutOfOrderness = 5000L;

        /**
         * 从数据源中抽取时间戳
         * @param s
         * @param l
         * @return
         */
        @Override
        public long extractTimestamp(String s, long l) {
            String[] split = s.split(",");
            long timeStamp  = Long.parseLong(split[1]);
            //设置当前最大时间
            currentTimeStamp = Math.max(timeStamp,currentTimeStamp);
            System.err.println(s + ",EventTime:" + timeStamp + ",watermark:" + (currentTimeStamp - maxOutOfOrderness));
            return timeStamp;
        }

        /**
         * 获取当前watermark，getCurrentWatermark 将会被 ExecutionConfig.setAutoWatermarkInterval 定义的时间定时调用
         * @return
         */
        @Nullable
        @Override
        public Watermark getCurrentWatermark() {
            //设置watermark为当前最大时间戳-最大允许的乱序范围
            return new Watermark(currentTimeStamp - maxOutOfOrderness);
        }
    }
}
