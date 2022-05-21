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
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;


public class BatchWordCountJob {

	public static void main(String[] args) throws Exception {
		// 获取执行环境
		ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
		// 获取数据源
		DataSet<String> stringDataSet = env.fromElements("i love benjing", "i love shanghai", "bejing is the captail of china");
        // 数据转换
		AggregateOperator<Tuple2<String, Integer>> operator = stringDataSet.flatMap(new MyFlatMapFunction()).groupBy(0).sum(1);
		// 数据输出
		operator.print();
		// 执行程序
		// 由于是Batch操作，当DataSet调用print方法时，源码内部已经调用Excute方法，所以此处不再调用，如果调用会出现错误
		env.execute("Flink Batch Java API Skeleton");
	}

	public static class MyFlatMapFunction implements FlatMapFunction<String,Tuple2<String,Integer>> {
		@Override
		public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
			String[] strings = s.split(" ");
			for (String word:strings) {
				collector.collect(new Tuple2<>(word,1));
			}
		}
	}


}
