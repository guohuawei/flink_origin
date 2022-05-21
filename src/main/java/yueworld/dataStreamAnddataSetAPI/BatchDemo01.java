package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.table.expressions.E;
import org.apache.flink.util.Collector;

/**
 * 下面的程序是一个完整的 WordCount 工作示例
 */
public class BatchDemo01 {
    public static void main(String[] args) throws Exception {
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSource<String> stringDataSource = env.fromElements("Who's there?",
                "I think I hear them. Stand, ho! Who's there?");

        FlatMapOperator<String, Tuple2<String, Integer>> flatMapData = stringDataSource.flatMap(new MyFlatMapFunction());

        AggregateOperator<Tuple2<String, Integer>> sumResult = flatMapData.groupBy(0).sum(1);

        sumResult.print();

    }

    public static class MyFlatMapFunction implements FlatMapFunction<String, Tuple2<String,Integer>>{

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {

            for (String word:s.split(" ")) {
                collector.collect(new Tuple2(word, 1));
            }
        }
    }
}
