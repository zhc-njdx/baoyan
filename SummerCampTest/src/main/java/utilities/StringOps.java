package utilities;

import java.util.Arrays;

/**
 * 常见的字符串操作
 */
public class StringOps {
    
    public static final String EMPTY_STRING = "";
    public static final String SPACE_STRING = " ";
    
    /**
     * 将多个字符使用指定连接符连接在一起
     * @param joiner 连接符
     * @param strings 需要连接的字符串
     * @return 连接后的字符串
     */
    public static String StringConcat(String joiner, String... strings) {
        return Arrays.stream(strings).reduce("", (s1, s2) -> s1 + joiner + s2);
    }
    
    /**
     * 反转字符串
     * @param str 待反转的字符串
     * @return 反转后的字符串
     */
    public static String StringReverse(String str) {
        if (isEmptyString(str)) return EMPTY_STRING;
        char[] chars = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = chars.length-1; i >= 0; i --) {
            sb.append(chars[i]);
        }
        return sb.toString();
    }
    
    /**
     * 将句子中的单词反转
     * @param line 句子
     * @return 反转后的句子
     */
    public static String reverseWordOrderInLine(String line) {
        if (isEmptyString(line)) return EMPTY_STRING;
        String[] words = line.split(SPACE_STRING);
        int length = words.length;
        ArrayUtils.reverseArray(words, 0, length-1);
        return StringConcat(SPACE_STRING, words);
    }
    
    /**
     * 判断一个字符串是否为空
     * @param str String
     * @return true为空
     */
    public static boolean isEmptyString(String str) {
        return str == null || str.length() == 0;
    }
    
    
}
