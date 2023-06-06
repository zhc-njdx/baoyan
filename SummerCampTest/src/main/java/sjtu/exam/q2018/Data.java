package sjtu.exam.q2018;

import java.util.Objects;

/**
 * csv文件中的一行数据对应的Model
 */
public class Data {
    String x;
    String y;
    String z;
    
    public Data(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Data data = (Data) o;
        return Objects.equals(x, data.x) && Objects.equals(y, data.y) && Objects.equals(z, data.z);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
    
    @Override
    public String toString() {
        return "("+x+", "+y+", "+z+")";
    }
}
