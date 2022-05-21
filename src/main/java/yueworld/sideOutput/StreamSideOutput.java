package yueworld.sideOutput;

import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

import java.util.ArrayList;

/**
 *  flink 分流
 */
public class StreamSideOutput {
    public static void main(String[] args) throws Exception {
        // 获取flin上下文环境
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 构造数据
        ArrayList<Tuple3<Integer, Integer, Integer>> list = new ArrayList<>();
        list.add(new Tuple3<>(1,1,2));
        list.add(new Tuple3<>(1,2,2));
        list.add(new Tuple3<>(1,3,2));
        list.add(new Tuple3<>(1,4,2));
        list.add(new Tuple3<>(2,2,3));
        list.add(new Tuple3<>(2,5,4));
        list.add(new Tuple3<>(2,23,5));
        list.add(new Tuple3<>(2,1,6));

        DataStreamSource<Tuple3<Integer, Integer, Integer>> streamSource = executionEnvironment.fromCollection(list);

        // 自定义分流标签
        OutputTag<Tuple3<Integer,Integer,Integer>> oneStream = new OutputTag<Tuple3<Integer,Integer,Integer>>("oneStream") {};
        OutputTag<Tuple3<Integer,Integer,Integer>> twoStream = new OutputTag<Tuple3<Integer,Integer,Integer>>("twoStream") {};

        // 将数据按照规则分流
        SingleOutputStreamOperator<Tuple3<Integer, Integer, Integer>> streamOperator = streamSource.process(new ProcessFunction<Tuple3<Integer, Integer, Integer>, Tuple3<Integer, Integer, Integer>>() {
            @Override
            public void processElement(Tuple3<Integer, Integer, Integer> value, Context context, Collector<Tuple3<Integer, Integer, Integer>> collector) throws Exception {
                if (value.f0 == 1) {
                    context.output(oneStream, value);
                } else if (value.f0 == 2) {
                    context.output(twoStream, value);
                }
            }
        });

        // 分别获取分流后的数据
        DataStream<Tuple3<Integer, Integer, Integer>> oneSideOutput = streamOperator.getSideOutput(oneStream);
        DataStream<Tuple3<Integer, Integer, Integer>> twoSideOutput = streamOperator.getSideOutput(twoStream);

        // 打印数据
        oneSideOutput.printToErr();
        twoSideOutput.print();

        executionEnvironment.execute(StreamSideOutput.class.getSimpleName());
    }

}



