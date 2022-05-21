package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

public class BatchDemo_reduce {
    public static void main(String[] args) throws Exception {
        // 获取flink执行上下文环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 构造数据源
        ArrayList<Tuple3<Integer, Integer, Integer>> list = new ArrayList<>();
        list.add(new Tuple3<>(0,1,0));
        list.add(new Tuple3<>(0,1,1));
        list.add(new Tuple3<>(0,2,2));
        list.add(new Tuple3<>(0,1,3));
        list.add(new Tuple3<>(1,2,5));
        list.add(new Tuple3<>(1,2,9));
        list.add(new Tuple3<>(1,2,11));
        list.add(new Tuple3<>(1,2,13));

        DataStreamSource<Tuple3<Integer, Integer, Integer>> tuple3DataStreamSource = environment.fromCollection(list);

        tuple3DataStreamSource.keyBy(0).reduce(new MyReduceFunction()).printToErr().setParallelism(1);

        environment.execute(BatchDemo_reduce.class.getSimpleName());

    }

    public static class MyReduceFunction implements ReduceFunction<Tuple3<Integer,Integer,Integer>>{

        @Override
        public Tuple3<Integer, Integer, Integer> reduce(Tuple3<Integer, Integer, Integer> t1, Tuple3<Integer, Integer, Integer> t2) throws Exception {
            Tuple3<Integer, Integer, Integer> tuple3 = new Tuple3<>();
            tuple3.setFields(0,0,(Integer)t1.getField(2) + (Integer)t2.getField(2));
            return tuple3;
        }
    }
}
