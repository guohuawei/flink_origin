package yueworld.sink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import redis.clients.jedis.Jedis;

/**
 *  自定义redis sink
 */
public class MyRedisSink extends RichSinkFunction<Tuple3<String,Integer,Integer>> {

    private transient Jedis jedis;

    @Override
    public void open(Configuration config) {

        ParameterTool parameters = (ParameterTool)getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        String host = parameters.getRequired("redis.host");
        String password = parameters.get("redis.password", "redis");
        Integer port = parameters.getInt("redis.port", 6381);
        Integer timeout = parameters.getInt("redis.timeout", 5000);
        Integer db = parameters.getInt("redis.db", 0);
        jedis = new Jedis(host, port, timeout);
        jedis.auth(password);
        jedis.select(db);
    }


    @Override
    public void invoke(Tuple3<String,Integer,Integer> value, Context context) throws Exception {
        if (!jedis.isConnected()) {
            jedis.connect();
        }
        System.out.println("------------------------------------------------------------" + value);
        //保存 key--value
        jedis.hset(value.f0,"pv",String.valueOf(value.f1));
        jedis.hset(value.f0,"uv",String.valueOf(value.f2));
    }

    @Override
    public void close() throws Exception {
        jedis.close();
    }
}
