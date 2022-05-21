/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package yueworld.wordCount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import scala.Tuple2;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="https://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class StreamingWordCountJob {

	public static void main(String[] args) throws Exception {
		// 创建执行环境
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

		// 从socket获取数据流
		DataStreamSource<String> dataStreamSource = env.socketTextStream("192.168.121.15", 8888);

		// 数据解析
		SingleOutputStreamOperator<WordWithCount> sum = dataStreamSource.flatMap(new MyFlatMapFunction())
				.keyBy("word")
				.timeWindow(Time.seconds(2), Time.seconds(2))
				.sum("count");
		// 输出数据到目的端
		sum.print();

		// 执行任务操作
		env.execute("Flink Streaming Java API Skeleton");
	}

	/**
	 * 自定义FlatMapFunction
	 */
	public static class MyFlatMapFunction implements FlatMapFunction<String, WordWithCount>{

		@Override
		public void flatMap(String s, Collector<WordWithCount> collector) throws Exception {
			String[] words = s.split(",");
			for (String world: words){
				// 将数据发送出去
				collector.collect(new WordWithCount(world,1));
			}
		}
	}

	/**
	 * 定义实体类
	 */
	public static class WordWithCount{
		public String word;
		public int count;

		public WordWithCount() {
		}

		public WordWithCount(String word, int count) {
			this.word = word;
			this.count = count;
		}

		public String getWord() {
			return word;
		}

		public void setWord(String word) {
			this.word = word;
		}

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}

		@Override
		public String toString() {
			return "WordWithCount{" +
					"word='" + word + '\'' +
					", count=" + count +
					'}';
		}
	}
}
