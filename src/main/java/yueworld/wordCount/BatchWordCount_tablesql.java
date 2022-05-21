package yueworld.wordCount;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.BatchTableEnvironment;


import java.util.ArrayList;

/**
 *  flink sql 计算wordCount
 */
public class BatchWordCount_tablesql {
    public static void main(String[] args) throws Exception {

        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // 创建一个tableEnvironment
        BatchTableEnvironment btEnv = BatchTableEnvironment.create(env);

        // 获取数据
        String words = "hello flink hello spark hello hbase";
        String[] split = words.split(" ");
        ArrayList<WordCount> list = new ArrayList<>();
        for (String word:split) {
            list.add(new WordCount(word,1));
        }
        DataSet<WordCount> input = env.fromCollection(list);

        // DataSet 转sql,指定字段名称
        Table table = btEnv.fromDataSet(input,"word,frequency");
        table.printSchema();

        // 将table注册为表
        btEnv.createTemporaryView("WordCountTable",table);

        // 执行sql 查询
        Table table1 = btEnv.sqlQuery("select word as word,sum(frequency) as frequency from WordCountTable group by word");

        // 将sql查询出来的结果转换为DataSet
        DataSet<WordCount> wordCountDataSet = btEnv.toDataSet(table1, WordCount.class);

        wordCountDataSet.printToErr();

    }

    public static class WordCount{
        public String word;
        public int frequency;

        public WordCount() {
        }

        public WordCount(String word, int frequency) {
            this.word = word;
            this.frequency = frequency;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public String toString() {
            return "WordCount{" +
                    "word='" + word + '\'' +
                    ", frequency=" + frequency +
                    '}';
        }
    }
}
