package sjtu.q2017;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search {
    private final Rule rule;
    private final FileContent content;
    // 结果
    private Map<String, Integer> occurrence; // 搜索词组出现的次数
    private List<String> result;
    private boolean success;
    
    public Search(Rule r, FileContent fc) {
        this.rule = r;
        this.content = fc;
        this.success = false;
    }

    /**
     * 进行实际的搜索匹配
     * 将文本中每一行与每一个搜索词组进行匹配
     */
    public void doSearch() {
        if (this.rule == null) return;
        occurrence = new HashMap<>();
        for (String r : rule.getRules()) {
            occurrence.put(r, 0);
        }
        result = new ArrayList<>();
        int lines = content.linesTotal();
        int ruleSize = rule.ruleSize();
        for (int i = 0; i < lines; i ++) {
            List<String> line = content.getLine(i);
            boolean lineHasAdd = false;
            for (int j = 0; j < ruleSize; j ++) { // 匹配每一个规则词组
                if (rule.match(line, j)) {
                    String r = rule.getRules().get(j);
                    occurrence.put(r, occurrence.get(r)+1);
                    if (!lineHasAdd) { // 不重复添加行
                        result.add(content.getLineStringWithLineNO(result.size()+1, i));
                        lineHasAdd = true;
                    }
                }
            }
        }
        this.success = true;
    }

    /**
     * 将搜索结果形成字符串
     * @return 结果字符串
     */
    @Override
    public String toString() {
        if (!this.success) return "Fail to search!";
        StringBuilder sb = new StringBuilder();
        // occurrences
        for (Map.Entry<String, Integer> entry : occurrence.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" occurrence");
            if (entry.getValue() > 1) sb.append("s");
            sb.append("\n");
        }
        // content
        for (String s : result) {
            sb.append(s).append("\n");
        }
        return sb.toString();
    }
}
