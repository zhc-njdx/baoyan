package sjtu.q2017;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理搜索参数-options 形成规则词组Rule
 */
public class OptionParser {
    
    private List<String> options;

    private static final int AND = 1; // &&
    private static final int OR = 2; // ||
    private static final int VALID = -1;
    
    public OptionParser(String opt) {
        preprocess(opt);
    }
    
    
    public Rule parse() {
        return parse0(0, options.size()-1);
    }

    /**
     * 递归处理options 形成规则词组列表
     * @param begin 处理的开始下标
     * @param end 处理的结束下标
     * @return 对应区间的options形成的Rule
     */
    private Rule parse0(int begin, int end) {
        Rule rule = null;
        Rule tmp = null;
        int i = begin;
        int op = VALID; // 规则之间的操作符
        while (i <= end) {
            String s = options.get(i);
            switch (s) {
                case "(":
                    // 遇到括号要进行递归处理括号中的搜索选项
                    int j = i;
                    int cnt = 1;
                    while (cnt != 0) {
                        i++;
                        s = options.get(i);
                        if (s.equals("(")) cnt ++;
                        if (s.equals(")")) cnt --;
                    }
                    tmp = parse0(j+1, i-1); // 递归
                    break;
                case "&&":
                    op = AND;
                    break;
                case "||":
                    op = OR;
                    break;
                case "!":
                    // 否定类型的直接将!加入规则词组中，后续再判断处理
                    i++;
                    String unit = "!" + options.get(i);
                    tmp = new Rule(unit);
                    break;
                default:
                    // options中的一个词
                    tmp = new Rule(s);
                    break;
            }
            if (rule == null && tmp != null) {
                // 形成的第一个规则词组 赋给rule
                rule = tmp;
                tmp = null;
            }
            if (tmp != null) {
                // 需要和rule进行合并
                if (op == AND) { // &&
                    rule.addAndRule(tmp);
                } else if (op == OR) { // ||
                    rule.addOrRule(tmp);
                }
                op = VALID;
                tmp = null;
            }
            i++;
        }
        return rule; // 形成的Rule
    }
    
    /**
     * 处理传进来的搜索选项字符串 将String解析成List 便于后续的解析
     */
    private void preprocess(String str){
        options = new ArrayList<>();
        int i = 0;
        int len = str.length();
        while (i < len) {
            char c = str.charAt(i);
            switch (c) {
                case '(':
                    options.add("(");
                    break;
                case ')':
                    options.add(")");
                    break;
                case '&':
                    options.add("&&");
                    i++;
                    break;
                case '|':
                    options.add("||");
                    i++;
                    break;
                case '!':
                    options.add("!");
                    break;
                default:
                    if (!WordOps.isLetter(c)) break; // 其他字符直接舍弃，包括空格、制表符等
                    int j = i;
                    while (WordOps.isLetter(c)) {
                        i ++;
                        if (i >= len) break;
                        c = str.charAt(i);
                    }
                    options.add(str.substring(j, i));
                    i--;
                    break;
            }
            i++;
        }
    }
}
