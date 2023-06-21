package sjtu.q2017;

import java.util.Set;

public class WordOps {
    
    private WordOps(){}

    /**
     * 对单词做小写化，如果以es ing ed s结尾，就去掉结尾
     * @param word 待处理的单词
     * @return 处理后的单词
     */
    public static String dealWord(String word) {
        String wordInLowerCase = word.toLowerCase();
        int len = wordInLowerCase.length();
        if (wordInLowerCase.endsWith("ing")) {
            return wordInLowerCase.substring(0, len-3);
        } else if (wordInLowerCase.endsWith("es")) {
            return wordInLowerCase.substring(0, len-2);
        } else if (wordInLowerCase.endsWith("ed")) {
            return wordInLowerCase.substring(0, len-2);
        } else if (wordInLowerCase.endsWith("s")) {
            return wordInLowerCase.substring(0, len-1);
        }
        return wordInLowerCase;
    }
    
    /**
     * 判断两个单词是否是同一个词原型
     * 例如: displayed 和 displaying 返回 true
     * @param word1 单词1
     * @param word2 单词2
     * @return 两个单词是否是同一个词原型
     */
    public static boolean isSameWord(String word1, String word2) {
        if (word1.equals(word2)) return true;
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        if (word1.equals(word2)) return true;
        if (word1.charAt(0) != word2.charAt(0)) return false;
        
        // w1, w2, word1, word2 中肯定有一个是原型词
        String w1 = dealWord(word1);
        String w2 = dealWord(word2);
    
        return w1.equals(w2) || w1.equals(word2) || word1.equals(w2);
    }
    
    /**
     * 查看与word同根的词是否已经在wordSet中，如果存在返回已经存在wordSet中的单词
     * 如果不存在，将word加进wordSet之后，返回word
     * @param word 待找的单词
     * @param wordSet 单词集合
     * @return 如果wordSet中存在和word同词根的单词，则返回该词；如果不存在，返回word
     */
    public static String sameWordInWordSet(String word, Set<String> wordSet) {
        for (String s : wordSet) {
            if (isSameWord(s, word))
                return s;
        }
        wordSet.add(word);
        return word;
    }

    /**
     * 判断 unit 和 word 是否匹配 不考虑词性的
     * unit = "!my", word = "you" => return true
     * unit = "you", word = "you" => return true
     * unit = "!my", word = "my"  => return false
     * @param unit 匹配word的pattern
     * @param word 单词
     * @return true为匹配，false为不匹配
     */
    public static boolean match(String unit, String word) {
        if (unit.startsWith("!")) {
            unit = unit.substring(1);
            return !isSameWord(unit, word);
        } else {
            return isSameWord(unit, word);
        }
    }

    /**
     * 判断一个字符是否是字母
     * @param c 字符
     * @return 是否是字母
     */
    public static boolean isLetter(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
}
