package yueworld.dataStreamAnddataSetAPI;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import yueworld.POJO.Person;

import java.util.ArrayList;

public class Stream02 {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment streamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();

        // DataStreamSource<Long> source = streamExecutionEnvironment.generateSequence(1,10);

//        ArrayList<Person> people = new ArrayList<>();
//        people.add(new Person("zhangsan",13));
//        people.add(new Person("lisi",34));
//        DataStreamSource<Person> source = streamExecutionEnvironment.fromCollection(people);


        DataStreamSource<Person> source = streamExecutionEnvironment.fromElements(new Person("zhangsan", 99));

        source.print();

        streamExecutionEnvironment.execute(Stream02.class.getSimpleName());
    }
}
