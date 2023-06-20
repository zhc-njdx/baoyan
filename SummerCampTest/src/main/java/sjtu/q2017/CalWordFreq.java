package sjtu.q2017;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CalWordFreq {
    private final FileContent fileContent;
    private Map<String, List<int[]>> freq;
    private Set<String> wordSet;
    
    public CalWordFreq(FileContent content){
        this.fileContent = content;
    }
    
    public void calc() {
        freq = new HashMap<>();
        wordSet = new HashSet<>();
        
        int lines = fileContent.linesTotal();
        for (int i = 0; i < lines; i ++) {
            int words = fileContent.wordsInLine(i);
            for (int j = 0; j < words; j ++) {
                String word = fileContent.getWord(i, j); // lowercase word
                // 将时态或者单复数的词词看成同一个
                word = WordOps.sameWordInWordSet(word, wordSet);
                freq.putIfAbsent(word, new ArrayList<>());
                freq.get(word).add(new int[]{i, j});
            }
        }
        
        toFile();
    }
    
    private void toFile() {
        String output = "result.txt";
        // 按字典序排列
        List<String> list = new ArrayList<>(wordSet);
        list.sort((String w1, String w2) -> {
            w1 = w1.toLowerCase();
            w2 = w2.toLowerCase();
            return w1.compareTo(w2);
        });
        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            for (String s : list) {
                StringBuilder line = new StringBuilder();
                line.append(s).append(" {");
                for (int[] ints : freq.get(s)) {
                    line.append("(").append(ints[0]).append(",").append(ints[1]).append("),");
                }
                line.deleteCharAt(line.length()-1).append("}\n");
                writer.write(line.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
