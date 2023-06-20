package sjtu.q2019;

import java.util.Objects;

public class KeyValue {
    
    private String key;
    private String value;
    
    public KeyValue(String k, String v) {
        key = k;
        value = v;
    }
    
    
    @Override
    public boolean equals(Object o) { // 注意重写equals函数 存在比较
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValue keyValue = (KeyValue) o;
        return Objects.equals(key, keyValue.key) &&
                   Objects.equals(value, keyValue.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getKey() {
        return key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setKV(KeyValue kv) {
        key = kv.getKey();
        value = kv.getValue();
    }
}
