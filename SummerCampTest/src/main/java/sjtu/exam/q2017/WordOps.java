package sjtu.exam.q2017;

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
     * 例如: displayed 和 displaying
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
     *
     * @param word 待找的单词
     * @param wordSet 单词集合
     * @return 如果wordSet中存在和word同词根的单词，则返回该词；反之，返回word本身
     */
    public static String sameWordInWordSet(String word, Set<String> wordSet) {
        for (String s : wordSet) {
            if (isSameWord(s, word))
                return s;
        }
        wordSet.add(word);
        return word;
    }
    
    public static boolean match(String unit, String word) {
        if (unit.startsWith("!")) {
            unit = unit.substring(1);
            return !isSameWord(unit, word);
        } else {
            return isSameWord(unit, word);
        }
    }
}
