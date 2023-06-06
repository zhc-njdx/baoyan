package sjtu.exam.q2017;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private List<String> rules;
    
    public Rule(List<String> r) {
        this.rules = r;
    }
    
    public Rule(String unit) {
        rules = new ArrayList<>();
        rules.add(unit);
    }
    
    public void addAndRule(Rule r) {
        List<String> newRule = new ArrayList<>();
        for (String r1 : rules) {
            for (String r2 : r.getRules()) {
                newRule.add(r1+" "+r2);
            }
        }
        this.rules = newRule;
    }
    
    public void addOrRule(Rule r) {
        this.rules.addAll(r.getRules());
    }
    
    public List<String> getRules() {
        return rules;
    }
    
    public void printRule(){
        for (String rule : rules) {
            System.out.println(rule);
        }
    }
    
    public int ruleSize() {
        return rules.size();
    }
    
    private boolean matchSingleUnAppear(List<String> line, String unit) {
        for (String word : line) {
            if (WordOps.isSameWord(word, unit))
                return false;
        }
        return true;
    }
    
    public boolean match(List<String> line, int ruleNo){
        String[] units = rules.get(ruleNo).split(" ");
        // 只有一个规则 !xxx: 需要一句话都没有出现过 xxx 才算匹配成功
        if (units.length == 1 && units[0].charAt(0) == '!') {
            return matchSingleUnAppear(line, units[0].substring(1));
        }
        for (int i = 0; i < line.size(); i ++) {
            String word = line.get(i);
            boolean matched = true;
            int j = i;
            for (int k = 0; k < units.length; k ++) {
                if (!WordOps.match(units[k], word)) {
                    matched = false;
                    break;
                } else {
                    j++;
                    // 已经到了该行最后一个单词
                    if (j == line.size()) {
                        // 如果也到了规则的最后一个单元 matched = true
                        // 反之说明不匹配
                        matched = k == units.length-1;
                        break;
                    }
                    word = line.get(j);
                }
            }
            if (matched) return true;
        }
        return false;
    }
    
    
}
