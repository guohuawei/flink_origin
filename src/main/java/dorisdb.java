import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import java.sql.*;

/**
 * 自定义mysql sink
 */
public class dorisdb {

    private static final long serialVersionUID = 1L;

    public static Connection connection;
    public static PreparedStatement preparedStatement;

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // JDBC连接信息
        String USERNAME = "root" ;
        String PASSWORD = "123456";
        String DBURL = "jdbc:mysql://10.251.8.4:9030/data_center";
        // 加载JDBC驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 获取数据库连接
        connection = DriverManager.getConnection(DBURL,USERNAME,PASSWORD);
        String sql = "show databases;";
        preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        int col = rs.getMetaData().getColumnCount();
        System.out.println("============================");
        while (rs.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(rs.getString(i) + "\t");
                if ((i == 2) && (rs.getString(i).length() < 8)) {
                    System.out.print("\t");
                }
            }
            System.out.println("");
        }
        System.out.print(rs);
    }



}
