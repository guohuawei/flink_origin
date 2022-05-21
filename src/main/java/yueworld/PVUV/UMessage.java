package yueworld.PVUV;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UMessage {

    public String uid;

    public String createTime;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public UMessage() {
    }

    public UMessage(String uid, String createTime) {
        this.uid = uid;
        this.createTime = createTime;
    }


}
