package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import yueworld.POJO.Person;

public class Example {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Person> source = env.fromElements(new Person("zhang san", 23), new Person("lisi", 34));

        DataStream<Person> filter = source.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.getAge() > 20;
            }
        });

        filter.print();

        env.execute(Example.class.getSimpleName());

    }
}
