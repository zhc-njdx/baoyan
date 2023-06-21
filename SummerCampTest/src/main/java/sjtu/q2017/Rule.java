package sjtu.q2017;

import java.util.ArrayList;
import java.util.List;

/**
 * 由搜索选项抽取出来的匹配词组
 * 例如 I && am => Rule{ I am }
 * ( father || mather ) && is => Rule { father is, mother is }
 * 抽取出来之后就可以用于搜索判断
 */
public class Rule {
    private List<String> rules; // 规则列表，其中的每个元素都是一个词组
    
    public Rule(List<String> r) {
        this.rules = r;
    }

    /**
     * 初始化规则
     * @param unit 初始规则
     */
    public Rule(String unit) {
        rules = new ArrayList<>();
        rules.add(unit);
    }

    /**
     * 增加&&规则
     * 例如 Rule{I, you} && Rule{ be } => Rule{I be, you be}
     * @param r 增加的规则
     */
    public void addAndRule(Rule r) {
        List<String> newRule = new ArrayList<>();
        for (String r1 : rules) {
            for (String r2 : r.getRules()) {
                newRule.add(r1+" "+r2);
            }
        }
        this.rules = newRule;
    }

    /**
     * 增加||规则
     * 例如 Rule{I, you} || Rule{ he } => Rule{I, you, he}
     * @param r 增加的规则
     */
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

    /**
     * 判断一个单词是否在一个句子中没有出现过
     * @param line 需要判断的行
     * @param unit 单词
     * @return true则unit没有在line中出现过 false则unit在line中出现过
     */
    private boolean matchSingleUnAppear(List<String> line, String unit) {
        for (String word : line) {
            if (WordOps.isSameWord(word, unit))
                return false;
        }
        return true;
    }

    /**
     * 判断在文本行中是否能够找到指定下标的规则词组
     * @param line 文本行
     * @param ruleNo 规则词组的下标
     * @return true / false
     */
    public boolean match(List<String> line, int ruleNo){
        String[] units = rules.get(ruleNo).split(" "); // 将规则词组拆分为词列表
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
                    break; // 发现不匹配的项 退出循环 从头开始
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
