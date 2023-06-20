package sjtu.q2017;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理搜索参数-options
 */
public class OptionParser {
    
    private List<String> options;
    
    public OptionParser(String opt) {
        preprocess(opt);
    }
    
    
    public Rule parse() {
        return parse0(0, options.size()-1);
    }
    
    private Rule parse0(int begin, int end) {
        Rule rule = null;
        Rule tmp = null;
        int i = begin;
        int op = -1;
        while (i <= end) {
            String s = options.get(i);
            switch (s) {
                case "(":
                    int j = i;
                    int cnt = 1;
                    while (cnt != 0) {
                        i++;
                        s = options.get(i);
                        if (s.equals("(")) cnt ++;
                        if (s.equals(")")) cnt --;
                    }
                    tmp = parse0(j+1, i-1);
                    break;
                case "&&":
                    op = 1;
                    break;
                case "||":
                    op = 2;
                    break;
                case "!":
                    i++;
                    String unit = "!" + options.get(i);
                    tmp = new Rule(unit);
                    break;
                default:
                    tmp = new Rule(s);
                    break;
            }
            if (rule == null && tmp != null) {
                rule = tmp;
                tmp = null;
            }
            if (op != -1 && tmp != null) {
                if (op == 1) { // &&
                    rule.addAndRule(tmp);
                } else { // ||
                    rule.addOrRule(tmp);
                }
                op = -1;
                tmp = null;
            }
            i++;
        }
        return rule;
    }
    
    /**
     * 处理options 将String解析成List<String>类型
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
                    if (!isLetter(c)) break; // 其他字符直接舍弃，包括空格、制表符等
                    int j = i;
                    while (isLetter(c)) {
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
    
    private boolean isLetter(char c) {
       return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
}
