package sjtu.exam.q2019;

public class LinearHashTable implements HashTable{
    
    private KeyValue[] table;
    private int capacity;
    private int elements;
    
    private static final int DEFAULT_CAPACITY = 8;
    
    public LinearHashTable() {
        capacity = DEFAULT_CAPACITY;
        table = new KeyValue[capacity];
    }
    
    private int hash(String key) {
        return (int) (Long.parseLong(key) % capacity);
    }
    
    @Override
    public void set(String key, String value) {
        if (elements > (capacity >> 1)) {
            grow();
        }
        int index = hash(key);
        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = increase(index);
        }
        if (table[index] == null) { // find the empty position
            table[index] = new KeyValue(key, value);
            elements++;
        } else { // overwrite the value
            table[index].setValue(value);
        }
    }
    
    @Override
    public String get(String key) {
        int index = hash(key);
        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = increase(index);
        }
        if (table[index] == null) {
            return "null";
        } else {
            return table[index].getValue();
        }
    }
    
    @Override
    public void delete(String key) {
        int index = hash(key);
        while (table[index] != null && !table[index].getKey().equals(key)) {
            index = increase(index);
        }
        if (table[index] == null) return; // not find
        table[index] = null;
        move(index);
    }
    
    /**
     * 删除了index位置的元素之后，需要移动后面的元素填补该空格
     * @param index 删除元素的位置
     */
    private void move(int index) {
        int i = increase(index);
        if (table[i] == null) return;
        int h = hash(table[i].getKey()); // the beginning pos of this element
        while (h > index) { // 原本就在空格的后面 不需要向前移
            i = increase(i);
            if (table[i] == null) break;
            h = hash(table[i].getKey());
        }
        if (table[i] == null) return;
        // 找到一个原本位置在index前面 将其移动到index位置
        table[index] = table[i];
        table[i] = null;
        move(i); // move next index
    }
    
    private int increase(int index) {
        return (index + 1) % capacity;
    }
    
    private void grow() {
        int oldCapacity = capacity;
        KeyValue[] oldTable = table;
        capacity = 2 * oldCapacity;
        table = new KeyValue[capacity];
        for (int i = 0; i < oldCapacity; i ++) {
            if (oldTable[i] != null)
                set(oldTable[i].getKey(), oldTable[i].getValue());
        }
    }
}
