package yueworld.eventSchema;

import net.sf.json.JSONObject;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import java.io.IOException;


/**
 *  自定义kafka内容 序列化与反序列化
 */
public class KafkaEventSchema implements DeserializationSchema<JSONObject>, SerializationSchema<JSONObject> {
    @Override
    public JSONObject deserialize(byte[] message) throws IOException {
        return JSONObject.fromObject(new String(message));
    }

    @Override
    public boolean isEndOfStream(JSONObject jsonObject) {
        return false;
    }

    @Override
    public TypeInformation<JSONObject> getProducedType() {
        return TypeInformation.of(JSONObject.class);
    }

    @Override
    public byte[] serialize(JSONObject jsonObject) {
        return jsonObject.toString().getBytes();
    }
}
