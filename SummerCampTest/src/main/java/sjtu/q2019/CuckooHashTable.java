package sjtu.q2019;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Deque;

public class CuckooHashTable implements HashTable{
    
    private KeyValue[] table1;
    private KeyValue[] table2;
    private int capacity;
    private List<KeyValue> kva;
    private Deque<Node> path;
    
    private static final int DEFAULT_CAPACITY = 8;
    
    public CuckooHashTable(){
        capacity = DEFAULT_CAPACITY;
        init();
    }
    
    private void init() {
        table1 = new KeyValue[capacity];
        table2 = new KeyValue[capacity];
        kva = new ArrayList<>();
    }
    
    
    private int h1(String key){
        return (int) (Long.parseLong(key) % capacity);
    }
    
    private int h2(String key) {
        return (int) ((Long.parseLong(key) / capacity) % capacity);
    }
    
    
    @Override
    public void set(String key, String value) {
        if (checkSet(key)) {
            set0(new KeyValue(key, value), false);
        } else {
            grow();
            set(key, value);
        }
    }
    
    /**
     * 实际的插入
     * @param kv0 要插入的KeyValue
     * @param extend 是否是在扩容阶段
     */
    private void set0(KeyValue kv0, boolean extend) {
        // 这个地方需要使用KeyValue对象 而不是 String key String value
        // 由于在扩容阶段需要使用这个函数来将之前的元素插入到扩容后的表中
        // 扩容阶段的插入是不能向插入新元素一样new一个KeyValue再插入的 而是要将原来的KeyValue对象插入到表中
        // 所以set0函数统一接收一个KeyValue对象
        // 如果是插入新元素就在set函数中将新KeyValue创建好传入set0
        // 如果是扩容插入就传入之前元素的引用
        
        path = null;
        
        String key = kv0.getKey();
        String value = kv0.getValue();
        
        int index1 = h1(key);
        int index2 = h2(key);
        if (table1[index1] != null && table1[index1].getKey().equals(key)) {
            table1[index1].setValue(value);
            return;
        }
        if (table2[index2] != null && table2[index2].getKey().equals(key)) {
            table2[index2].setValue(value);
            return;
        }
        if (table1[index1] == null) {
            table1[index1] = kv0;
            if (!extend) kva.add(table1[index1]);
            return;
        }
        // h1 position is not empty, need to move
        Deque<Node> path0 = new ArrayDeque<>();
        
        KeyValue kv = table1[index1];
        int ft = 1;
        int fi = index1;
        int tt = 2;
        int ti = h2(kv.getKey());
        path0.push(new Node(kv, ft, fi, tt, ti));
        while (true) {
            KeyValue[] table = tt == 1 ? table1 : table2;
            kv = table[ti];
            if (kv == null) break;
            ft = tt;
            fi = ti;
            tt = ft == 1 ? 2 : 1;
            ti = tt == 1 ? h1(kv.getKey()) : h2(kv.getKey());
            path0.push(new Node(kv, ft, fi, tt, ti));
        }
        path = new ArrayDeque<>(path0); // record the path of the move
        // find a path to move
        while (!path0.isEmpty()) {
            Node node = path0.pop();
            move(node.getFt(), node.getFi(), node.getTt(), node.getTi());
        }
        table1[index1] = kv0;
        if (!extend) kva.add(table1[index1]);
    }
    
    /**
     * 检查插入key能够成功
     * @param key 待插入的key
     * @return true可以成功插入 false出现环不能插入
     */
    private boolean checkSet(String key) {
        int index1 = h1(key);
        int index2 = h2(key);
        // overwrite
        if (table1[index1] != null && table1[index1].getKey().equals(key)) return true;
        if (table2[index2] != null && table2[index2].getKey().equals(key)) return true;
        // insert into the empty
        if (table1[index1] == null) return true;
        
        KeyValue kv = table1[index1];
        int ft = 1;
        int fi = index1;
        int tt = 2;
        int ti = h2(kv.getKey());
        Set<String> keySet = new HashSet<>();
        keySet.add(kv.getKey());
        // kv need to move from table#ft index#fi to table#tt index#ti
        while (true) {
            KeyValue[] table = tt == 1 ? table1 : table2;
            kv = table[ti];
            if (kv == null || keySet.contains(kv.getKey())) break; // 出现空位或者出现环
            ft = tt;
            fi = ti;
            tt = ft == 1 ? 2 : 1;
            ti = tt == 1 ? h1(kv.getKey()) : h2(kv.getKey());
            keySet.add(kv.getKey());
        }
        return kv == null; // true when has empty, false when has a loop
    }
    
    private void move(int ft, int fi, int tt, int ti) {
        if (ft == 2 && tt == 1) {
            table1[ti] = table2[fi];
        } else if (ft == 1 && tt == 2){
            table2[ti] = table1[fi];
        } else {
            System.err.println("Wrong Moving!");
        }
    }
    
    @Override
    public String get(String key) {
        int index1 = h1(key);
        int index2 = h2(key);
        if (table1[index1] != null && table1[index1].getKey().equals(key)) return table1[index1].getValue();
        if (table2[index2] != null && table2[index2].getKey().equals(key)) return table2[index2].getValue();
        return "null";
    }
    
    @Override
    public void delete(String key) {
        int index1 = h1(key);
        int index2 = h2(key);
        if (table1[index1] != null && table1[index1].getKey().equals(key)) {
            kva.remove(table1[index1]);
            table1[index1] = null;
        } else if (table2[index2] != null && table2[index2].getKey().equals(key)) {
            kva.remove(table2[index2]);
            table2[index2] = null;
        }
    }
    
    private void grow() {
        extendCapacity();
        while (existLoopAfterGrow()) {
            extendCapacity();
        }
    }
    
    private void extendCapacity() {
        capacity *= 2;
        table1 = new KeyValue[capacity];
        table2 = new KeyValue[capacity];
    }
    
    private boolean existLoopAfterGrow() {
        for (KeyValue kv : kva) {
            if (checkSet(kv.getKey())) {
                set0(kv, true);
            } else {
                return true;
            }
        }
        return false;
    }
    
     // =============================================================
     // 支持GUI的方法
     //==============================================================
    
    public String getKeyByIndex(int tableNo, int index) {
        KeyValue[] table = tableNo == 1 ? table1 : table2;
        if (table[index] == null) return "null";
        return table[index].getKey();
    }
    
    public Deque<Node> getPath() {
        return path;
    }
}
