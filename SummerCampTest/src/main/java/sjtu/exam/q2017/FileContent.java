package sjtu.exam.q2017;

import java.util.ArrayList;
import java.util.List;

/**
 * 全权负责文本内容获取
 */
public class FileContent {
    
    private final List<List<String>> content;
    
    public FileContent(List<String> file) {
        content = new ArrayList<>();
        /*
         * 将单词从句子中拆分出来 注意标点符号也需要分割出来
         */
        for (String line : file) {
            List<String> line0 = new ArrayList<>();
            int i = 0;
            int len = line.length();
            while (i < len) {
                char c = line.charAt(i);
                if (isLetter(c)) {
                    StringBuilder sb = new StringBuilder();
                    while (isLetter(c)) {
                        sb.append(c);
                        i++;
                        if (i >= len) break;
                        c = line.charAt(i);
                    }
                    String word = sb.toString().toLowerCase();
                    if (isWord(word)) line0.add(word);
                }
                i++;
            }
            content.add(line0);
        }
    }
    
    private boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    private boolean isWord(String word) {
        if (word.length() == 1) {
            return word.equals("a") || word.equals("i") || word.equals("u");
        }
        return true;
    }
    
    public int linesTotal() {
        return content.size();
    }
    
    public int wordsInLine(int lineNo) {
        return content.get(lineNo).size();
    }
    
    public String getWord(int lineNo, int wordNo) {
        return content.get(lineNo).get(wordNo);
    }
    
    public List<String> getLine(int lineNo) {return content.get(lineNo);}
    
    public String getLineStringWithLineNO(int index, int lineNo) {
        StringBuilder sb = new StringBuilder();
        sb.append(index).append(".(").append(lineNo).append(")");
        for (String word : content.get(lineNo)) {
            sb.append(word).append(" ");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
