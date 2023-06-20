package sjtu.q2020;

public class Record {
    private String keyword;
    private int count;
    
    public Record(String keyword) {
        this.keyword = keyword;
        this.count = 1;
    }
    
    public Record(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }
    
    public String getKeyword() {
        return keyword;
    }
    
    public int getCount() {
        return count;
    }
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    public void setCount(int count) {
        this.count = count;
    }
    
    public void incrementCount() {
        this.count++;
    }
}
