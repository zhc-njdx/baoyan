package sjtu.q2017;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Search {
    private final Rule rule;
    private final FileContent content;
    // 结果
    private Map<String, Integer> occurrence;
    private List<String> result;
    
    public Search(Rule r, FileContent fc) {
        this.rule = r;
        this.content = fc;
    }
    
    public void doSearch() {
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
            for (int j = 0; j < ruleSize; j ++) {
                if (rule.match(line, j)) {
                    String r = rule.getRules().get(j);
                    occurrence.put(r, occurrence.get(r)+1);
                    if (!lineHasAdd) {
                        result.add(content.getLineStringWithLineNO(result.size()+1, i));
                        lineHasAdd = true;
                    }
                }
            }
        }
    }
    
    @Override
    public String toString() {
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
