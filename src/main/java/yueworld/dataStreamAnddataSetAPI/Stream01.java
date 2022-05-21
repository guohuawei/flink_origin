package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.core.execution.JobClient;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.expressions.E;
import org.apache.kafka.common.protocol.types.Field;

public class Stream01 {
    public static void main(String[] args) throws Exception {
        // 获取flink执行环境
        final StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 从文件读取数据
        DataStreamSource<String> streamSource = executionEnvironment.readTextFile("file:///C:\\Users\\ghw20\\Desktop\\代码\\flink_java\\src\\main\\resources\\myConf.properties");


        // 数据转换
        SingleOutputStreamOperator<Tuple2<String, String>> mapSource = streamSource.filter(new MyfilterFun()).map(new MyMapFun());


        // 打印数据
        mapSource.print();

        // 数据写到本地文件
        mapSource.writeAsText("file:///C:\\Users\\ghw20\\Desktop\\代码\\flink_java\\src\\main\\resources\\t.txt");

        // 执行程序
        executionEnvironment.execute(Stream01.class.getSimpleName());

    }


    // 自定义filter函数
    public static class MyfilterFun implements FilterFunction<String>
    {

        @Override
        public boolean filter(String s) throws Exception {
            return s.length()>0;
        }
    }

    // 自定义map函数

    public static class MyMapFun implements MapFunction<String,Tuple2<String,String>>
    {

        @Override
        public Tuple2<String, String> map(String s) throws Exception {
            return new Tuple2<>(s.split("=")[0],s.split("=")[1]);
        }
    }

}
