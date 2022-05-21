package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.runtime.rest.messages.job.savepoints.SavepointTriggerRequestBody;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import yueworld.POJO.Item;

import javax.crypto.spec.OAEPParameterSpec;

/**
 *  flink 自定义实时数据源 测试
 */
public class StreamingDemo {
    public static void main(String[] args) throws Exception {
        // 创建flink运行上下文环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

        // 获取数据源 ,并且设置并行度为1
        DataStreamSource<Item> dataStreamSource = environment.addSource(new MyStreamingSource()).setParallelism(1);

        // 逻辑运算 map
//        SingleOutputStreamOperator<String> operator = dataStreamSource.map(new MyMapFunction());
        //  在map函数中使用lambda表达式 效果和自定义mapFunction一样
//        SingleOutputStreamOperator<String> operator = dataStreamSource.map(item -> item.getName());

        // flatMap
//        SingleOutputStreamOperator<String> operator = dataStreamSource.flatMap(new MyFlatMapFunction());


        // filter
        // SingleOutputStreamOperator<Item> operator = dataStreamSource.filter(new MyFilterFunction());
        SingleOutputStreamOperator<Item> operator = dataStreamSource.filter(item -> item.getId() % 2 == 0);

        // 打印结果
        operator.printToErr().setParallelism(1);

        // 执行程序
        environment.execute(StreamingDemo.class.getSimpleName());
    }


    /**
     *  自定义mapFunction函数
     */
    public static class MyMapFunction implements MapFunction<Item, String> {
       @Override
       public String map(Item item) throws Exception {
           return item.getName();
       }
   }

    /**
     *  自定义FlatMapFunction函数
     */
   public static class MyFlatMapFunction implements FlatMapFunction<Item,String>{

       @Override
       public void flatMap(Item item, Collector<String> collector) throws Exception {
           collector.collect(item.getName());
       }
   }

    /**
     * 自定义FilterFunction函数
     */
   public static class MyFilterFunction implements FilterFunction<Item>{
       @Override
       public boolean filter(Item item) throws Exception {
           return item.getId() % 2 == 0;
       }
   }



}
