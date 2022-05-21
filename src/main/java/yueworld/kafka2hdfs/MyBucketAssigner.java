package yueworld.kafka2hdfs;

import org.apache.flink.core.io.SimpleVersionedSerializer;
import org.apache.flink.streaming.api.functions.sink.filesystem.BucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.SimpleVersionedStringSerializer;
import yueworld.POJO.BlackList;

/**
 * 自定义分桶策略
 */
public class MyBucketAssigner implements BucketAssigner<String, String> {

    @Override
    public String getBucketId(String blackList, Context context) {
        String[] split = blackList.split(",");
        return split[1].substring(0,10);
    }

    @Override
    public SimpleVersionedSerializer<String> getSerializer() {
        return  SimpleVersionedStringSerializer.INSTANCE;
    }
}
