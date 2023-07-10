package sjtu.q2020;

public class Record implements Comparable<Record>{
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
    
    public void incrementCount() {
        this.count++;
    }

    @Override
    public int compareTo(Record r) {
        if (count != r.count) return count - r.count;
        return r.keyword.compareTo(keyword);
    }

    @Override
    public String toString() {
        return String.format("%s %d", keyword, count);
    }
}
