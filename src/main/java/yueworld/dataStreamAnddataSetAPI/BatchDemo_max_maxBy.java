package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;

/**
 *  聚合函数 max 和 maxBy的使用
 */
public class BatchDemo_max_maxBy {
    public static void main(String[] args) throws Exception {
        // 获取flink执行上下文环境
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();

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

        DataSource<Tuple3<Integer, Integer, Integer>> tuple3DataStreamSource = environment.fromCollection(list);
        tuple3DataStreamSource.groupBy(0).max(2).printToErr();
    }
}
