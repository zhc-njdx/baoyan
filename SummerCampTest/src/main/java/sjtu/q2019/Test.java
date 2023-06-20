package sjtu.q2019;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Test {
    public static void correctTest(String input, String output, HashTable ht) {
        try (BufferedReader reader = new BufferedReader(new FileReader(input));
             BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            String line;
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                String[] s = line.split(" ");
                switch (s[0]) {
                    case "Set":
                        ht.set(s[1], s[2]);
                        break;
                    case "Get":
                        String res = ht.get(s[1]);
                        writer.write(res+"\n");
                        break;
                    case "Del":
                        ht.delete(s[1]);
                        break;
                    default:
                        System.err.println("Wrong Operator!");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        String smallInput = "small.in";
        String largeInput = "large.in";
        String smallAnswer = "small.ans";
        String largeAnswer = "large.ans";
        Test.correctTest(smallInput, "cuckoo_small_out.ans", new CuckooHashTable());
        Test.correctTest(smallInput, "linear_small_out.ans", new LinearHashTable());
        Test.correctTest(largeInput, "cuckoo_large_out.ans", new CuckooHashTable());
        Test.correctTest(largeInput, "linear_large_out.ans", new LinearHashTable());

        Utils.fc("linear_small_out.ans", smallAnswer);
        Utils.fc("cuckoo_small_out.ans", smallAnswer);
        Utils.fc("linear_large_out.ans", largeAnswer);
        Utils.fc("cuckoo_large_out.ans", largeAnswer);
    }
}
