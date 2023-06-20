package sjtu.q2019;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerformanceTest {
    Random random;
    HashTable ht;
    List<String> test0Result;
    List<String> test1Result;
    List<String> test2Result;
    
    private static final int TOTAL_NUMBER = 10000;
    
    public PerformanceTest(HashTable ht) {
        random = new Random();
        this.ht = ht;
    }
    
    private String generateUnsignedIntString() {
        long key = random.nextLong();
        return (Math.abs(key) >>> 32) + "";
    }
    
    public void init() {
        String[] keys = new String[TOTAL_NUMBER];
        String[] values = new String[TOTAL_NUMBER];
        for (int i = 0; i < TOTAL_NUMBER; i ++) {
            keys[i] = generateUnsignedIntString();
            values[i] = generateUnsignedIntString();
        }
        // test0
        test0Result = new ArrayList<>();
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            long s = System.currentTimeMillis();
            ht.set(keys[i], values[i]);
            long e = System.currentTimeMillis();
            long delay = e - s;
            test0Result.add(delay+"");
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        for (int i = 1000; i < TOTAL_NUMBER; i++) {
            ht.set(keys[i], values[i]);
        }
    }
    
    /**
     * AllGet
     */
    public void testAllGet() {
        test1Result = new ArrayList<>();
        // generate the random keys
        String[] randomKeys = new String[TOTAL_NUMBER];
        for (int i = 0; i < TOTAL_NUMBER; i ++) {
            randomKeys[i] = generateUnsignedIntString();
        }
        long minDelay = Long.MAX_VALUE;
        long maxDelay = Long.MIN_VALUE;
        long begin = System.currentTimeMillis();
        for (int i = 0; i < TOTAL_NUMBER; i ++) {
            long s = System.currentTimeMillis();
            ht.get(randomKeys[i]);
            long e = System.currentTimeMillis();
            long delay = e - s;
            if (delay > maxDelay) maxDelay = delay;
            if (delay < minDelay) minDelay = delay;
        }
        long end = System.currentTimeMillis();
        double averageDelay = (double) (end - begin) / TOTAL_NUMBER;
        double throughput = (double)TOTAL_NUMBER / (end - begin) * 1000;
        
        test1Result.add(throughput+"");
        test1Result.add(minDelay+"");
        test1Result.add(maxDelay+"");
        test1Result.add(averageDelay+"");
    }
    
    /**
     * HalfGetHalfSet
     */
    public void testHalfGetHalfSet() {
        test2Result = new ArrayList<>();
        String[] randomKeys = new String[TOTAL_NUMBER / 2];
        String[] randomValues = new String[TOTAL_NUMBER / 2];
        for (int i = 0; i < TOTAL_NUMBER / 2; i ++) {
            randomKeys[i] = generateUnsignedIntString();
            randomValues[i] = generateUnsignedIntString();
        }
        long minDelay = Long.MAX_VALUE;
        long maxDelay = Long.MIN_VALUE;
        long begin = System.currentTimeMillis();
        // get
        for (int i = 0; i < TOTAL_NUMBER / 2; i ++) {
            long s = System.currentTimeMillis();
            ht.get(randomKeys[i]);
            long e = System.currentTimeMillis();
            long delay = e - s;
            if (delay > maxDelay) maxDelay = delay;
            if (delay < minDelay) minDelay = delay;
        }
        // set
        for (int i = 0; i < TOTAL_NUMBER / 2; i ++) {
            long s = System.currentTimeMillis();
            ht.set(randomKeys[i], randomValues[i]);
            long e = System.currentTimeMillis();
            long delay = e - s;
            if (delay > maxDelay) maxDelay = delay;
            if (delay < minDelay) minDelay = delay;
        }
        long end = System.currentTimeMillis();
        double averageDelay = (double) (end - begin) / TOTAL_NUMBER;
        double throughput = (double) TOTAL_NUMBER / (end - begin) * 1000;
    
        test2Result.add(throughput+"");
        test2Result.add(minDelay+"");
        test2Result.add(maxDelay+"");
        test2Result.add(averageDelay+"");
    }
    
    public void writeIntoFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("test0: delay for first 1000 set\n");
            for (String s : test0Result) {
                writer.write(s+"\n");
            }
            writer.write("test1: all get\n");
            for (String s : test1Result) {
                writer.write(s+"\n");
            }
            writer.write("test2: half set and half get\n");
            for (String s : test2Result) {
                writer.write(s+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        PerformanceTest linearTest = new PerformanceTest(new LinearHashTable());
        linearTest.init();
        linearTest.testAllGet();
        linearTest.testHalfGetHalfSet();
        linearTest.writeIntoFile("linear_performance.txt");
        
        PerformanceTest cuckooTest = new PerformanceTest(new CuckooHashTable());
        cuckooTest.init();
        cuckooTest.testAllGet();
        cuckooTest.testHalfGetHalfSet();
        cuckooTest.writeIntoFile("cuckoo_performance.txt");
    }
}
