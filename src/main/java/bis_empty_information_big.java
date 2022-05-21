// ORM class for table 'bis_empty_information_big'
// WARNING: This class is AUTO-GENERATED. Modify at your own risk.
//
// Debug information:
// Generated date: Tue Sep 14 14:34:38 CST 2021
// For connector: org.apache.sqoop.manager.MySQLManager
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;
import org.apache.sqoop.lib.JdbcWritableBridge;
import org.apache.sqoop.lib.DelimiterSet;
import org.apache.sqoop.lib.FieldFormatter;
import org.apache.sqoop.lib.RecordParser;
import org.apache.sqoop.lib.BooleanParser;
import org.apache.sqoop.lib.BlobRef;
import org.apache.sqoop.lib.ClobRef;
import org.apache.sqoop.lib.LargeObjectLoader;
import org.apache.sqoop.lib.SqoopRecord;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class bis_empty_information_big extends SqoopRecord  implements DBWritable, Writable {
  private final int PROTOCOL_VERSION = 3;
  public int getClassFormatVersion() { return PROTOCOL_VERSION; }
  public static interface FieldSetterCommand {    void setField(Object value);  }  protected ResultSet __cur_result_set;
  private Map<String, FieldSetterCommand> setters = new HashMap<String, FieldSetterCommand>();
  private void init0() {
    setters.put("area_name", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.area_name = (String)value;
      }
    });
    setters.put("area_id", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.area_id = (Long)value;
      }
    });
    setters.put("bis_project_id", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.bis_project_id = (String)value;
      }
    });
    setters.put("project_name", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.project_name = (String)value;
      }
    });
    setters.put("bis_store_id", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.bis_store_id = (String)value;
      }
    });
    setters.put("store_no", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.store_no = (String)value;
      }
    });
    setters.put("rent_square", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.rent_square = (java.math.BigDecimal)value;
      }
    });
    setters.put("empty_days", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.empty_days = (Integer)value;
      }
    });
    setters.put("store_type", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.store_type = (String)value;
      }
    });
    setters.put("is_assess", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.is_assess = (String)value;
      }
    });
    setters.put("bis_floor_id", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.bis_floor_id = (String)value;
      }
    });
    setters.put("floor_num", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.floor_num = (String)value;
      }
    });
    setters.put("id", new FieldSetterCommand() {
      @Override
      public void setField(Object value) {
        bis_empty_information_big.this.id = (Long)value;
      }
    });
  }
  public bis_empty_information_big() {
    init0();
  }
  private String area_name;
  public String get_area_name() {
    return area_name;
  }
  public void set_area_name(String area_name) {
    this.area_name = area_name;
  }
  public bis_empty_information_big with_area_name(String area_name) {
    this.area_name = area_name;
    return this;
  }
  private Long area_id;
  public Long get_area_id() {
    return area_id;
  }
  public void set_area_id(Long area_id) {
    this.area_id = area_id;
  }
  public bis_empty_information_big with_area_id(Long area_id) {
    this.area_id = area_id;
    return this;
  }
  private String bis_project_id;
  public String get_bis_project_id() {
    return bis_project_id;
  }
  public void set_bis_project_id(String bis_project_id) {
    this.bis_project_id = bis_project_id;
  }
  public bis_empty_information_big with_bis_project_id(String bis_project_id) {
    this.bis_project_id = bis_project_id;
    return this;
  }
  private String project_name;
  public String get_project_name() {
    return project_name;
  }
  public void set_project_name(String project_name) {
    this.project_name = project_name;
  }
  public bis_empty_information_big with_project_name(String project_name) {
    this.project_name = project_name;
    return this;
  }
  private String bis_store_id;
  public String get_bis_store_id() {
    return bis_store_id;
  }
  public void set_bis_store_id(String bis_store_id) {
    this.bis_store_id = bis_store_id;
  }
  public bis_empty_information_big with_bis_store_id(String bis_store_id) {
    this.bis_store_id = bis_store_id;
    return this;
  }
  private String store_no;
  public String get_store_no() {
    return store_no;
  }
  public void set_store_no(String store_no) {
    this.store_no = store_no;
  }
  public bis_empty_information_big with_store_no(String store_no) {
    this.store_no = store_no;
    return this;
  }
  private java.math.BigDecimal rent_square;
  public java.math.BigDecimal get_rent_square() {
    return rent_square;
  }
  public void set_rent_square(java.math.BigDecimal rent_square) {
    this.rent_square = rent_square;
  }
  public bis_empty_information_big with_rent_square(java.math.BigDecimal rent_square) {
    this.rent_square = rent_square;
    return this;
  }
  private Integer empty_days;
  public Integer get_empty_days() {
    return empty_days;
  }
  public void set_empty_days(Integer empty_days) {
    this.empty_days = empty_days;
  }
  public bis_empty_information_big with_empty_days(Integer empty_days) {
    this.empty_days = empty_days;
    return this;
  }
  private String store_type;
  public String get_store_type() {
    return store_type;
  }
  public void set_store_type(String store_type) {
    this.store_type = store_type;
  }
  public bis_empty_information_big with_store_type(String store_type) {
    this.store_type = store_type;
    return this;
  }
  private String is_assess;
  public String get_is_assess() {
    return is_assess;
  }
  public void set_is_assess(String is_assess) {
    this.is_assess = is_assess;
  }
  public bis_empty_information_big with_is_assess(String is_assess) {
    this.is_assess = is_assess;
    return this;
  }
  private String bis_floor_id;
  public String get_bis_floor_id() {
    return bis_floor_id;
  }
  public void set_bis_floor_id(String bis_floor_id) {
    this.bis_floor_id = bis_floor_id;
  }
  public bis_empty_information_big with_bis_floor_id(String bis_floor_id) {
    this.bis_floor_id = bis_floor_id;
    return this;
  }
  private String floor_num;
  public String get_floor_num() {
    return floor_num;
  }
  public void set_floor_num(String floor_num) {
    this.floor_num = floor_num;
  }
  public bis_empty_information_big with_floor_num(String floor_num) {
    this.floor_num = floor_num;
    return this;
  }
  private Long id;
  public Long get_id() {
    return id;
  }
  public void set_id(Long id) {
    this.id = id;
  }
  public bis_empty_information_big with_id(Long id) {
    this.id = id;
    return this;
  }
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof bis_empty_information_big)) {
      return false;
    }
    bis_empty_information_big that = (bis_empty_information_big) o;
    boolean equal = true;
    equal = equal && (this.area_name == null ? that.area_name == null : this.area_name.equals(that.area_name));
    equal = equal && (this.area_id == null ? that.area_id == null : this.area_id.equals(that.area_id));
    equal = equal && (this.bis_project_id == null ? that.bis_project_id == null : this.bis_project_id.equals(that.bis_project_id));
    equal = equal && (this.project_name == null ? that.project_name == null : this.project_name.equals(that.project_name));
    equal = equal && (this.bis_store_id == null ? that.bis_store_id == null : this.bis_store_id.equals(that.bis_store_id));
    equal = equal && (this.store_no == null ? that.store_no == null : this.store_no.equals(that.store_no));
    equal = equal && (this.rent_square == null ? that.rent_square == null : this.rent_square.equals(that.rent_square));
    equal = equal && (this.empty_days == null ? that.empty_days == null : this.empty_days.equals(that.empty_days));
    equal = equal && (this.store_type == null ? that.store_type == null : this.store_type.equals(that.store_type));
    equal = equal && (this.is_assess == null ? that.is_assess == null : this.is_assess.equals(that.is_assess));
    equal = equal && (this.bis_floor_id == null ? that.bis_floor_id == null : this.bis_floor_id.equals(that.bis_floor_id));
    equal = equal && (this.floor_num == null ? that.floor_num == null : this.floor_num.equals(that.floor_num));
    equal = equal && (this.id == null ? that.id == null : this.id.equals(that.id));
    return equal;
  }
  public boolean equals0(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof bis_empty_information_big)) {
      return false;
    }
    bis_empty_information_big that = (bis_empty_information_big) o;
    boolean equal = true;
    equal = equal && (this.area_name == null ? that.area_name == null : this.area_name.equals(that.area_name));
    equal = equal && (this.area_id == null ? that.area_id == null : this.area_id.equals(that.area_id));
    equal = equal && (this.bis_project_id == null ? that.bis_project_id == null : this.bis_project_id.equals(that.bis_project_id));
    equal = equal && (this.project_name == null ? that.project_name == null : this.project_name.equals(that.project_name));
    equal = equal && (this.bis_store_id == null ? that.bis_store_id == null : this.bis_store_id.equals(that.bis_store_id));
    equal = equal && (this.store_no == null ? that.store_no == null : this.store_no.equals(that.store_no));
    equal = equal && (this.rent_square == null ? that.rent_square == null : this.rent_square.equals(that.rent_square));
    equal = equal && (this.empty_days == null ? that.empty_days == null : this.empty_days.equals(that.empty_days));
    equal = equal && (this.store_type == null ? that.store_type == null : this.store_type.equals(that.store_type));
    equal = equal && (this.is_assess == null ? that.is_assess == null : this.is_assess.equals(that.is_assess));
    equal = equal && (this.bis_floor_id == null ? that.bis_floor_id == null : this.bis_floor_id.equals(that.bis_floor_id));
    equal = equal && (this.floor_num == null ? that.floor_num == null : this.floor_num.equals(that.floor_num));
    equal = equal && (this.id == null ? that.id == null : this.id.equals(that.id));
    return equal;
  }
  public void readFields(ResultSet __dbResults) throws SQLException {
    this.__cur_result_set = __dbResults;
    this.area_name = JdbcWritableBridge.readString(1, __dbResults);
    this.area_id = JdbcWritableBridge.readLong(2, __dbResults);
    this.bis_project_id = JdbcWritableBridge.readString(3, __dbResults);
    this.project_name = JdbcWritableBridge.readString(4, __dbResults);
    this.bis_store_id = JdbcWritableBridge.readString(5, __dbResults);
    this.store_no = JdbcWritableBridge.readString(6, __dbResults);
    this.rent_square = JdbcWritableBridge.readBigDecimal(7, __dbResults);
    this.empty_days = JdbcWritableBridge.readInteger(8, __dbResults);
    this.store_type = JdbcWritableBridge.readString(9, __dbResults);
    this.is_assess = JdbcWritableBridge.readString(10, __dbResults);
    this.bis_floor_id = JdbcWritableBridge.readString(11, __dbResults);
    this.floor_num = JdbcWritableBridge.readString(12, __dbResults);
    this.id = JdbcWritableBridge.readLong(13, __dbResults);
  }
  public void readFields0(ResultSet __dbResults) throws SQLException {
    this.area_name = JdbcWritableBridge.readString(1, __dbResults);
    this.area_id = JdbcWritableBridge.readLong(2, __dbResults);
    this.bis_project_id = JdbcWritableBridge.readString(3, __dbResults);
    this.project_name = JdbcWritableBridge.readString(4, __dbResults);
    this.bis_store_id = JdbcWritableBridge.readString(5, __dbResults);
    this.store_no = JdbcWritableBridge.readString(6, __dbResults);
    this.rent_square = JdbcWritableBridge.readBigDecimal(7, __dbResults);
    this.empty_days = JdbcWritableBridge.readInteger(8, __dbResults);
    this.store_type = JdbcWritableBridge.readString(9, __dbResults);
    this.is_assess = JdbcWritableBridge.readString(10, __dbResults);
    this.bis_floor_id = JdbcWritableBridge.readString(11, __dbResults);
    this.floor_num = JdbcWritableBridge.readString(12, __dbResults);
    this.id = JdbcWritableBridge.readLong(13, __dbResults);
  }
  public void loadLargeObjects(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void loadLargeObjects0(LargeObjectLoader __loader)
      throws SQLException, IOException, InterruptedException {
  }
  public void write(PreparedStatement __dbStmt) throws SQLException {
    write(__dbStmt, 0);
  }

  public int write(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(area_name, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeLong(area_id, 2 + __off, -5, __dbStmt);
    JdbcWritableBridge.writeString(bis_project_id, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(project_name, 4 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(bis_store_id, 5 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(store_no, 6 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(rent_square, 7 + __off, 3, __dbStmt);
    JdbcWritableBridge.writeInteger(empty_days, 8 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(store_type, 9 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(is_assess, 10 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(bis_floor_id, 11 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(floor_num, 12 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeLong(id, 13 + __off, -5, __dbStmt);
    return 13;
  }
  public void write0(PreparedStatement __dbStmt, int __off) throws SQLException {
    JdbcWritableBridge.writeString(area_name, 1 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeLong(area_id, 2 + __off, -5, __dbStmt);
    JdbcWritableBridge.writeString(bis_project_id, 3 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(project_name, 4 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(bis_store_id, 5 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(store_no, 6 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeBigDecimal(rent_square, 7 + __off, 3, __dbStmt);
    JdbcWritableBridge.writeInteger(empty_days, 8 + __off, 4, __dbStmt);
    JdbcWritableBridge.writeString(store_type, 9 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(is_assess, 10 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(bis_floor_id, 11 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeString(floor_num, 12 + __off, 12, __dbStmt);
    JdbcWritableBridge.writeLong(id, 13 + __off, -5, __dbStmt);
  }
  public void readFields(DataInput __dataIn) throws IOException {
this.readFields0(__dataIn);  }
  public void readFields0(DataInput __dataIn) throws IOException {
    if (__dataIn.readBoolean()) { 
        this.area_name = null;
    } else {
    this.area_name = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.area_id = null;
    } else {
    this.area_id = Long.valueOf(__dataIn.readLong());
    }
    if (__dataIn.readBoolean()) { 
        this.bis_project_id = null;
    } else {
    this.bis_project_id = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.project_name = null;
    } else {
    this.project_name = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.bis_store_id = null;
    } else {
    this.bis_store_id = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.store_no = null;
    } else {
    this.store_no = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.rent_square = null;
    } else {
    this.rent_square = org.apache.sqoop.lib.BigDecimalSerializer.readFields(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.empty_days = null;
    } else {
    this.empty_days = Integer.valueOf(__dataIn.readInt());
    }
    if (__dataIn.readBoolean()) { 
        this.store_type = null;
    } else {
    this.store_type = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.is_assess = null;
    } else {
    this.is_assess = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.bis_floor_id = null;
    } else {
    this.bis_floor_id = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.floor_num = null;
    } else {
    this.floor_num = Text.readString(__dataIn);
    }
    if (__dataIn.readBoolean()) { 
        this.id = null;
    } else {
    this.id = Long.valueOf(__dataIn.readLong());
    }
  }
  public void write(DataOutput __dataOut) throws IOException {
    if (null == this.area_name) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, area_name);
    }
    if (null == this.area_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.area_id);
    }
    if (null == this.bis_project_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, bis_project_id);
    }
    if (null == this.project_name) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, project_name);
    }
    if (null == this.bis_store_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, bis_store_id);
    }
    if (null == this.store_no) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, store_no);
    }
    if (null == this.rent_square) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    org.apache.sqoop.lib.BigDecimalSerializer.write(this.rent_square, __dataOut);
    }
    if (null == this.empty_days) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.empty_days);
    }
    if (null == this.store_type) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, store_type);
    }
    if (null == this.is_assess) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, is_assess);
    }
    if (null == this.bis_floor_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, bis_floor_id);
    }
    if (null == this.floor_num) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, floor_num);
    }
    if (null == this.id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.id);
    }
  }
  public void write0(DataOutput __dataOut) throws IOException {
    if (null == this.area_name) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, area_name);
    }
    if (null == this.area_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.area_id);
    }
    if (null == this.bis_project_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, bis_project_id);
    }
    if (null == this.project_name) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, project_name);
    }
    if (null == this.bis_store_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, bis_store_id);
    }
    if (null == this.store_no) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, store_no);
    }
    if (null == this.rent_square) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    org.apache.sqoop.lib.BigDecimalSerializer.write(this.rent_square, __dataOut);
    }
    if (null == this.empty_days) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeInt(this.empty_days);
    }
    if (null == this.store_type) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, store_type);
    }
    if (null == this.is_assess) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, is_assess);
    }
    if (null == this.bis_floor_id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, bis_floor_id);
    }
    if (null == this.floor_num) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    Text.writeString(__dataOut, floor_num);
    }
    if (null == this.id) { 
        __dataOut.writeBoolean(true);
    } else {
        __dataOut.writeBoolean(false);
    __dataOut.writeLong(this.id);
    }
  }
  private static final DelimiterSet __outputDelimiters = new DelimiterSet((char) 1, (char) 10, (char) 0, (char) 0, false);
  public String toString() {
    return toString(__outputDelimiters, true);
  }
  public String toString(DelimiterSet delimiters) {
    return toString(delimiters, true);
  }
  public String toString(boolean useRecordDelim) {
    return toString(__outputDelimiters, useRecordDelim);
  }
  public String toString(DelimiterSet delimiters, boolean useRecordDelim) {
    StringBuilder __sb = new StringBuilder();
    char fieldDelim = delimiters.getFieldsTerminatedBy();
    __sb.append(FieldFormatter.escapeAndEnclose(area_name==null?"null":area_name, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(area_id==null?"null":"" + area_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(bis_project_id==null?"null":bis_project_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(project_name==null?"null":project_name, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(bis_store_id==null?"null":bis_store_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(store_no==null?"null":store_no, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(rent_square==null?"null":rent_square.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(empty_days==null?"null":"" + empty_days, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(store_type==null?"null":store_type, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_assess==null?"null":is_assess, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(bis_floor_id==null?"null":bis_floor_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(floor_num==null?"null":floor_num, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(id==null?"null":"" + id, delimiters));
    if (useRecordDelim) {
      __sb.append(delimiters.getLinesTerminatedBy());
    }
    return __sb.toString();
  }
  public void toString0(DelimiterSet delimiters, StringBuilder __sb, char fieldDelim) {
    __sb.append(FieldFormatter.escapeAndEnclose(area_name==null?"null":area_name, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(area_id==null?"null":"" + area_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(bis_project_id==null?"null":bis_project_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(project_name==null?"null":project_name, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(bis_store_id==null?"null":bis_store_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(store_no==null?"null":store_no, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(rent_square==null?"null":rent_square.toPlainString(), delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(empty_days==null?"null":"" + empty_days, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(store_type==null?"null":store_type, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(is_assess==null?"null":is_assess, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(bis_floor_id==null?"null":bis_floor_id, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(floor_num==null?"null":floor_num, delimiters));
    __sb.append(fieldDelim);
    __sb.append(FieldFormatter.escapeAndEnclose(id==null?"null":"" + id, delimiters));
  }
  private static final DelimiterSet __inputDelimiters = new DelimiterSet((char) 1, (char) 10, (char) 0, (char) 0, false);
  private RecordParser __parser;
  public void parse(Text __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharSequence __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(byte [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(char [] __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(ByteBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  public void parse(CharBuffer __record) throws RecordParser.ParseError {
    if (null == this.__parser) {
      this.__parser = new RecordParser(__inputDelimiters);
    }
    List<String> __fields = this.__parser.parseRecord(__record);
    __loadFromFields(__fields);
  }

  private void __loadFromFields(List<String> fields) {
    Iterator<String> __it = fields.listIterator();
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.area_name = null; } else {
      this.area_name = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.area_id = null; } else {
      this.area_id = Long.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.bis_project_id = null; } else {
      this.bis_project_id = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.project_name = null; } else {
      this.project_name = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.bis_store_id = null; } else {
      this.bis_store_id = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.store_no = null; } else {
      this.store_no = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.rent_square = null; } else {
      this.rent_square = new java.math.BigDecimal(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.empty_days = null; } else {
      this.empty_days = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.store_type = null; } else {
      this.store_type = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.is_assess = null; } else {
      this.is_assess = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.bis_floor_id = null; } else {
      this.bis_floor_id = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.floor_num = null; } else {
      this.floor_num = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.id = null; } else {
      this.id = Long.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  private void __loadFromFields0(Iterator<String> __it) {
    String __cur_str = null;
    try {
    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.area_name = null; } else {
      this.area_name = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.area_id = null; } else {
      this.area_id = Long.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.bis_project_id = null; } else {
      this.bis_project_id = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.project_name = null; } else {
      this.project_name = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.bis_store_id = null; } else {
      this.bis_store_id = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.store_no = null; } else {
      this.store_no = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.rent_square = null; } else {
      this.rent_square = new java.math.BigDecimal(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.empty_days = null; } else {
      this.empty_days = Integer.valueOf(__cur_str);
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.store_type = null; } else {
      this.store_type = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.is_assess = null; } else {
      this.is_assess = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.bis_floor_id = null; } else {
      this.bis_floor_id = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N")) { this.floor_num = null; } else {
      this.floor_num = __cur_str;
    }

    if (__it.hasNext()) {
        __cur_str = __it.next();
    } else {
        __cur_str = "\\N";
    }
    if (__cur_str.equals("\\N") || __cur_str.length() == 0) { this.id = null; } else {
      this.id = Long.valueOf(__cur_str);
    }

    } catch (RuntimeException e) {    throw new RuntimeException("Can't parse input data: '" + __cur_str + "'", e);    }  }

  public Object clone() throws CloneNotSupportedException {
    bis_empty_information_big o = (bis_empty_information_big) super.clone();
    return o;
  }

  public void clone0(bis_empty_information_big o) throws CloneNotSupportedException {
  }

  public Map<String, Object> getFieldMap() {
    Map<String, Object> __sqoop$field_map = new HashMap<String, Object>();
    __sqoop$field_map.put("area_name", this.area_name);
    __sqoop$field_map.put("area_id", this.area_id);
    __sqoop$field_map.put("bis_project_id", this.bis_project_id);
    __sqoop$field_map.put("project_name", this.project_name);
    __sqoop$field_map.put("bis_store_id", this.bis_store_id);
    __sqoop$field_map.put("store_no", this.store_no);
    __sqoop$field_map.put("rent_square", this.rent_square);
    __sqoop$field_map.put("empty_days", this.empty_days);
    __sqoop$field_map.put("store_type", this.store_type);
    __sqoop$field_map.put("is_assess", this.is_assess);
    __sqoop$field_map.put("bis_floor_id", this.bis_floor_id);
    __sqoop$field_map.put("floor_num", this.floor_num);
    __sqoop$field_map.put("id", this.id);
    return __sqoop$field_map;
  }

  public void getFieldMap0(Map<String, Object> __sqoop$field_map) {
    __sqoop$field_map.put("area_name", this.area_name);
    __sqoop$field_map.put("area_id", this.area_id);
    __sqoop$field_map.put("bis_project_id", this.bis_project_id);
    __sqoop$field_map.put("project_name", this.project_name);
    __sqoop$field_map.put("bis_store_id", this.bis_store_id);
    __sqoop$field_map.put("store_no", this.store_no);
    __sqoop$field_map.put("rent_square", this.rent_square);
    __sqoop$field_map.put("empty_days", this.empty_days);
    __sqoop$field_map.put("store_type", this.store_type);
    __sqoop$field_map.put("is_assess", this.is_assess);
    __sqoop$field_map.put("bis_floor_id", this.bis_floor_id);
    __sqoop$field_map.put("floor_num", this.floor_num);
    __sqoop$field_map.put("id", this.id);
  }

  public void setField(String __fieldName, Object __fieldVal) {
    if (!setters.containsKey(__fieldName)) {
      throw new RuntimeException("No such field:"+__fieldName);
    }
    setters.get(__fieldName).setField(__fieldVal);
  }

}
