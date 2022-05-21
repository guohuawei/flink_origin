package yueworld.kafkaUtils;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.text.SimpleDateFormat;
import java.util.Random;

public class Test {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //通过Socket实时获取数据
        DataStreamSource<String> dataStreamSource = env.socketTextStream("localhost", 8888);

        SingleOutputStreamOperator<Tuple2<String, Integer>> streamOperator = dataStreamSource.map(str -> Tuple2.of(str, 1)).returns(Types.TUPLE(Types.STRING, Types.INT));
        //keyBy() Tuple元组以下标的形式,进行分组
        KeyedStream<Tuple2<String, Integer>, Tuple> keyedStream = streamOperator.keyBy(0);
        //对分组后的数据进行 reduce() 操作
        //old,news 为两个 Tuple2<String,Integer>类型(通过f0,f1可以获得相对应下标的值)
        SingleOutputStreamOperator<Tuple2<String, Integer>> count = keyedStream.reduce(new ReduceFunction<Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> reduce(Tuple2<String, Integer> stringIntegerTuple2, Tuple2<String, Integer> t1) throws Exception {
                return null;
            }
        });

        count.print();

        env.execute("ReduceDemo");

    }
}
