package yueworld.POJO;
 // 数据格式：{"id":1,"createTime":"2020-06-05 14:03:16","name":"A001",age:34,"address":"B001"}
public class BlackList {
    public Integer id;
    public String createTime;
    public String name;
    public Integer age;
    public String address;

     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public String getCreateTime() {
         return createTime;
     }

     public void setCreateTime(String createTime) {
         this.createTime = createTime;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public Integer getAge() {
         return age;
     }

     public void setAge(Integer age) {
         this.age = age;
     }

     public String getAddress() {
         return address;
     }

     public void setAddress(String address) {
         this.address = address;
     }

     public BlackList() {
     }

     public BlackList(Integer id, String createTime, String name, Integer age, String address) {
         this.id = id;
         this.createTime = createTime;
         this.name = name;
         this.age = age;
         this.address = address;
     }

     @Override
     public String toString() {
         return
                 id +","+
                 createTime +","+
                         name +","+  age +","+ address ;
     }
 }
