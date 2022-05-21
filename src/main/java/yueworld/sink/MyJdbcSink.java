package yueworld.sink;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * 自定义mysql sink
 */
public class MyJdbcSink extends RichSinkFunction<Tuple3<String, Integer, Integer>>{

    private static final long serialVersionUID = 1L;

    private Connection connection;
    private PreparedStatement preparedStatement;

    /**
     * open方法是初始化方法，会在invoke方法之前执行，执行一次。
     */
    @Override
    public void open(Configuration parameters) throws Exception {
        // JDBC连接信息
        String USERNAME = "root" ;
        String PASSWORD = "yueworlddata";
        String DBURL = "jdbc:mysql://192.168.121.127:3306/test";
        // 加载JDBC驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 获取数据库连接
        connection = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
        String sql = "REPLACE INTO pv_uv(myDate,pv,uv) values (?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        super.open(parameters);
    }

    /**
     * close()是tear down的方法，在销毁时执行，关闭连接。
     */
    @Override
    public void close() throws Exception {
        if(preparedStatement != null){
            preparedStatement.close();
        }
        if(connection != null){
            connection.close();
        }
        super.close();
    }

    @Override
    public void invoke(Tuple3<String, Integer, Integer> value, Context context) throws Exception {
        try {
            preparedStatement.setString(1,value.f0);
            preparedStatement.setInt(2,value.f1.intValue()); // pv
            preparedStatement.setInt(3,value.f2.intValue()); // uv
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
