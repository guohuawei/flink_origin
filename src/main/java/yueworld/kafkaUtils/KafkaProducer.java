package yueworld.kafkaUtils;

import net.sf.json.JSONObject;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import static java.lang.Thread.sleep;

/**
 * kafka生产者
 */
public class KafkaProducer {
	public static void main(String [] args)  {

		Properties props = new Properties();
		props.put("bootstrap.servers", "10.251.8.2:9092,10.251.8.5:9092,10.251.8.4:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());
		props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getCanonicalName());

		Producer<String,String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
		java.util.Random points =new java.util.Random(2);

		for(int i = 0; i < 10000; i++){
			// 发送消息
			//SendMsg_Json(producer);

//			producer.send(new ProducerRecord<>("side",String.valueOf(points.nextInt(10)) ));
//			SendMsg_KV(producer);
//			SendMsg_KafkaEvent(producer);
//			SendMsg_FlinkEngine(producer);
			//SendMsg_Json_kafla(producer);

			SendMsg_Json_blackList_kafla(producer);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		producer.close();
	}

	public static void SendMsg_Json(Producer<String, String> producer ){
		java.util.Random points =new java.util.Random(2);
		List list = new ArrayList<String>();
		list.add("apple");
		list.add("pear");
		list.add("nut");
		list.add("grape");
		list.add("banana");
		list.add("pineapple");
		list.add("pomelo");
		list.add("orange");

		for(int i = 0; i < 3000; i++){

			JSONObject json = new JSONObject();
			json.put("fruit",list.get(points.nextInt(8)));
			json.put("number",points.nextInt(4));
			json.put("time",System.currentTimeMillis());
			json.put("number",points.nextInt(4));
			producer.send(new ProducerRecord<String, String>("jsontest",String.valueOf(i), json.toString()));

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void SendMsg_KV(Producer<String, String> producer ){
		java.util.Random points =new java.util.Random(2);
		List list = new ArrayList<String>();
		list.add("apple");
		list.add("pear");
		list.add("nut");
		list.add("grape");
		list.add("banana");
		list.add("pineapple");
		list.add("pomelo");
		list.add("orange1");

		for(int i = 0; i < 3000; i++){
			String str = list.get(points.nextInt(8))+" "+points.nextInt(5);
			producer.send(new ProducerRecord<>("KV",String.valueOf(i), str));
//			str = list.get(points.nextInt(8))+" "+points.nextInt(5);
//			producer.send(new ProducerRecord<>("KV1",String.valueOf(i), str));
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void SendMsg_KafkaEvent(Producer<String, String> producer ){
		java.util.Random points =new java.util.Random(2);
		List list = new ArrayList<String>();
		list.add("apple");
		list.add("pear");
		list.add("nut");
		list.add("grape");
		list.add("banana");
		list.add("pineapple");
		list.add("pomelo");
		list.add("orange");


		for(int i = 0; i < 3000; i++){
			String str = list.get(points.nextInt(8))+","+points.nextInt(5)+","+System.currentTimeMillis();
			producer.send(new ProducerRecord<>("test",String.valueOf(i), str));

			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void SendMsg_FlinkEngine(Producer<String, String> producer ){
		java.util.Random points =new java.util.Random(2);
		List list = new ArrayList<String>();
		list.add("apple");
		list.add("nut");
		list.add("banana");
		list.add("orange");


		for(int i = 0; i < 3000; i++){
			JSONObject json = new JSONObject();
			json.put("fruit",list.get(points.nextInt(4)));
			json.put("number",points.nextInt(4));
			json.put("time",System.currentTimeMillis());
			producer.send(new ProducerRecord<String, String>("FlinkEngine",String.valueOf(i), json.toString()));

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 数据格式：{"uid":"uid7707","createTime":"2020-06-05 14:03:16"}
	 * @param producer
	 */
	public static void SendMsg_Json_kafla(Producer<String, String> producer ){

		for(int i = 0; i < 3000; i++){
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = dateformat.format(System.currentTimeMillis());
			int num = new Random().nextInt(10000);
			JSONObject json = new JSONObject();
			json.put("uid","uid" + num);
			json.put("createTime",dateStr);
			producer.send(new ProducerRecord<String, String>("pvuvTopic",String.valueOf(i), json.toString()));

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * 数据格式：{"id":1,"createTime":"2020-06-05 14:03:16","name":"A001",age:34,"address":"B001"}
	 * @param producer
	 */
	public static void SendMsg_Json_blackList_kafla(Producer<String, String> producer ){

		for(int i = 0; i < 3000; i++){
			SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = dateformat.format(System.currentTimeMillis());
			int num = new Random().nextInt(30);
			JSONObject json = new JSONObject();
			json.put("createTime", dateStr);
			json.put("id", num);
			json.put("name","A00" +num);
			json.put("age",2 * num);
			json.put("address","B00" + num);
			producer.send(new ProducerRecord<String, String>("blackListTopic",String.valueOf(i), json.toString()));

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
