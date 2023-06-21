package sjtu.q2017;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本内容
 */
public class FileContent {
    
    private final List<List<String>> content; // 使用单词集合的形式表示文本内容

    /**
     * 初始化
     * @param file 文本内容，行列表
     */
    public FileContent(List<String> file) {
        content = new ArrayList<>();
        /*
         * 将单词从句子中拆分出来 注意要除去标点符号
         */
        for (String line : file) {
            List<String> line0 = new ArrayList<>();
            int i = 0;
            int len = line.length();
            while (i < len) {
                char c = line.charAt(i);
                if (WordOps.isLetter(c)) {
                    StringBuilder sb = new StringBuilder();
                    while (WordOps.isLetter(c)) {
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

    private boolean isWord(String word) {
        if (word.length() == 1) {
            return word.equals("a") || word.equals("i") || word.equals("u");
        }
        return true;
    }
    
    public int linesTotal() {
        return content.size();
    }
    
    public int wordNumberInLine(int lineNo) {
        return content.get(lineNo).size();
    }
    
    public String getWord(int lineNo, int wordNo) {
        return content.get(lineNo).get(wordNo);
    }
    
    public List<String> getLine(int lineNo) {return content.get(lineNo);}

    /**
     * 用于输出匹配上的文本行信息
     * @param index 标号
     * @param lineNo 指定行下标
     * @return 指定格式({index}.({lineNo})lineString)的字符串
     */
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
