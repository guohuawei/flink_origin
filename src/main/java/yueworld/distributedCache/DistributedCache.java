package yueworld.distributedCache;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.configuration.Configuration;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * flink 分布式缓存
 */
public class DistributedCache {
    public static void main(String[] args) throws Exception {
        // 获取flink执行化境上下文
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        // 注册分布式缓存文件
        String path = "C:\\yueShangWorkSpace\\flink_java\\src\\main\\resources\\content.txt";
        environment.registerCachedFile(path,"distributedCacheFile");

        // 获取数据
        DataSource<String> dataSource = environment.fromElements("flink", "hbase", "storm", "java", "spark");

        MapOperator<String, String> mapOperator = dataSource.map(new MyMapFunction());

        mapOperator.printToErr();
    }


    /**
     * 自定义mapFunction
     */
    public static class MyMapFunction extends RichMapFunction<String, String> {

        private ArrayList<String> dataList = new ArrayList<String>();

        @Override
        public void open(Configuration parameters) throws Exception {
            super.open(parameters);
            // 获取分布式缓存文件
            File cacheFile = getRuntimeContext().getDistributedCache().getFile("distributedCacheFile");
            List<String> lines = FileUtils.readLines(cacheFile);
            for (String line:lines) {
                dataList.add(line);
                System.err.println("分布式缓存为：" + line);
            }
        }

        @Override
        public String map(String s) throws Exception {
            // 在这里就可以使用dataList
            return dataList+":"+s;
        }
    }
}
