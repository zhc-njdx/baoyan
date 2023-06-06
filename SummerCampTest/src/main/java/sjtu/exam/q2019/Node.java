package sjtu.exam.q2019;

public class Node {
    private KeyValue kv;
    private int ft;
    private int fi;
    private int tt;
    private int ti;
    
    public Node(KeyValue kv, int ft, int fi, int tt, int ti) {
        this.kv = kv;
        this.ft = ft;
        this.fi = fi;
        this.tt = tt;
        this.ti = ti;
    }
    
    public void setKv(KeyValue kv) {
        this.kv = kv;
    }
    
    public void setFt(int ft) {
        this.ft = ft;
    }
    
    public void setFi(int fi) {
        this.fi = fi;
    }
    
    public void setTt(int tt) {
        this.tt = tt;
    }
    
    public void setTi(int ti) {
        this.ti = ti;
    }
    
    public KeyValue getKv() {
        return kv;
    }
    
    public int getFt() {
        return ft;
    }
    
    public int getFi() {
        return fi;
    }
    
    public int getTt() {
        return tt;
    }
    
    public int getTi() {
        return ti;
    }
}
