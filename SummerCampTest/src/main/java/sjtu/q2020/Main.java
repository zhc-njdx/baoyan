package sjtu.q2020;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static final String dir = "D:\\study\\BaoYan\\postgraduate-recommendation\\assets\\sjtu-se\\2020机考\\small-case\\";
    private Map<String, List<Record>> userKeywordCounts; // user -> record[]
    private List<Record> ranking; // record
    Map<Integer, String> idx2uid;
    private int[][] relations;
    
    public Main() {
        userKeywordCounts = new HashMap<>();
    }
    
    /**
     * 按照规定格式读取日志文件，统计日志文件中出现的每个用户搜索过的每个词的次数
     */
    public void loadKeywordCounts() {
        readLogs();
    }
    
    private void readLogs() {
        int totalLines = 0;
        try(Scanner in = new Scanner(System.in)){
            int n = in.nextInt();
            for (int i = 1; i <= n; i ++) {
                String file = dir + "keywords-" + i + ".log";
                totalLines += readSingleLog(file);
                System.out.println(totalLines + " " + userKeywordCounts.size());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int readSingleLog(String file) {
        int lineCnt = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] s = line.split(" ");
                String uid = s[0];
                String keyword = s[1];
                putIntoMap(uid, keyword);
                lineCnt ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineCnt;
    }
    
    private void putIntoMap(String uid, String keyword) {
        if (userKeywordCounts.containsKey(uid)) {
            List<Record> records = userKeywordCounts.get(uid);
            boolean isExist = false;
            for (Record r : records) {
                if (r.getKeyword().equals(keyword)) {
                    isExist = true;
                    r.incrementCount();
                }
            }
            if (!isExist) {
                records.add(new Record(keyword));
            }
        } else {
            List<Record> records = new ArrayList<>();
            records.add(new Record(keyword));
            userKeywordCounts.put(uid, records);
        }
    }
    
    public void rankKeywords() {
        Map<String, Integer> keywords = new HashMap<>();
        userKeywordCounts.forEach((uid, records) -> {
            for (Record r : records) {
                String kw = r.getKeyword();
                int cnt = r.getCount();
                keywords.put(kw, keywords.getOrDefault(kw, 0)+cnt);
            }
        });
        ranking = new ArrayList<>();
        keywords.forEach((kw, cnt) -> ranking.add(new Record(kw, cnt)));
        ranking.sort((r1, r2) -> {
            if (r1.getCount() != r2.getCount()) return r2.getCount() - r1.getCount();
            return r1.getKeyword().compareTo(r2.getKeyword());
        });
        for (int i = 0; i < 5; i ++) {
            String kw = ranking.get(i).getKeyword();
            int cnt = ranking.get(i).getCount();
            System.out.println(kw + " " + cnt);
        }
    }
    
    public void calcRelations() {
        idx2uid = new HashMap<>();
        final int[] idx = {0};
        userKeywordCounts.forEach((uid, records) -> {
            idx2uid.put(idx[0], uid);
            idx[0]++;
        });
        relations = new int[idx[0]][idx[0]];
        List<Relation> rankingRelations = new ArrayList<>();
        for (int i = 0; i < idx[0]; i ++) {
            for (int j = 0; j < idx[0]; j ++) {
                String a = idx2uid.get(i);
                String b = idx2uid.get(j);
                relations[i][j] = calc(a, b);
                if (j > i) rankingRelations.add(new Relation(a, b, relations[i][j]));
            }
        }
        rankingRelations.sort((r1, r2) -> {
            if (r1.value != r2.value) return r2.value - r1.value;
            if (r1.minUser().compareTo(r2.minUser()) != 0) return r1.minUser().compareTo(r2.minUser());
            return r1.maxUser().compareTo(r2.maxUser());
        });
        System.out.println(rankingRelations.get(0).minUser() + " " + rankingRelations.get(0).maxUser() + " " + rankingRelations.get(0).value);
    }
    
    private int calc(String userA, String userB) {
        if (userA.equals(userB)) return -1;
        int n = ranking.size();
        int relation = 0;
        for (int i = 0; i < n; i ++) {
            String k = ranking.get(i).getKeyword();
            int countA = 0;
            int countB = 0;
            for (Record r : userKeywordCounts.get(userA)) {
                if (r.getKeyword().equals(k)) countA += r.getCount();
            }
            for (Record r : userKeywordCounts.get(userB)) {
                if (r.getKeyword().equals(k)) countB += r.getCount();
            }
            relation += Math.min(countA, countB)*(n-i);
        }
        return relation;
    }
    
    static class Relation {
        String userA;
        String userB;
        int value;
        public Relation(String a, String b, int value) {
            userA = a;
            userB = b;
            this.value = value;
        }
        public String minUser() {
            if (userA.compareTo(userB) > 0) return userB;
            return userA;
        }
        public String maxUser() {
            if (userA.compareTo(userB) > 0) return userA;
            return userB;
        }
    }
    
    public void calcRecom() {
        Map<String, List<Relation>> userRelations = new HashMap<>();
        for (int i = 0; i < relations.length; i ++) {
            for (int j = 0; j < relations[0].length; j ++) {
                if (j == i) continue;
                String userA = idx2uid.get(i);
                String userB = idx2uid.get(j);
                userRelations.putIfAbsent(userA, new ArrayList<>());
                userRelations.get(userA).add(new Relation(userA, userB, relations[i][j]));
            }
        }
        List<String> recommendations = new ArrayList<>();
        Map<String, Integer> recommendWordCount = new HashMap<>();
        userRelations.forEach((user, relation) -> calcUserRelation(user, relation, recommendations, recommendWordCount));
        // 写进文件
        writeRecomsIntoFile(recommendations);
        int maxCount = -1;
        String maxKeyword = "";
        for (Map.Entry<String, Integer> entry : recommendWordCount.entrySet()) {
            if (maxCount < entry.getValue() || (maxCount == entry.getValue() && entry.getKey().compareTo(maxKeyword) < 0)) {
                maxKeyword = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        System.out.println(maxKeyword + " " + maxCount);
    }
    
    private void calcUserRelation(String user, List<Relation> relation, List<String> recommendations, Map<String, Integer> recommendWordCount) {
        // 排序
        relation.sort((r1, r2) -> {
            if (r1.value != r2.value) return r2.value - r1.value;
            return r1.userB.compareTo(r2.userB);
        });
        // 获取A的所有keyword
        Set<String> recordsA = new HashSet<>();
        for (Record r : userKeywordCounts.get(user)) {
            recordsA.add(r.getKeyword());
        }
        Set<String> allRecords = new HashSet<>();
        // 将关联最近的用户的Record合并
        for (int i = 0; i < Math.min(3, relation.size()); i ++) {
            for (Record r : userKeywordCounts.get(relation.get(i).userB)) {
                if (!recordsA.contains(r.getKeyword()))
                    allRecords.add(r.getKeyword());
            }
        }
        // 推荐词排序
        List<String> allRecordList = new ArrayList<>(allRecords);
        allRecordList.sort((kw1, kw2) -> {
            int i1 = indexOfKeyword(kw1);
            int i2 = indexOfKeyword(kw2);
            return i1-i2;
        });
        // 写成一行
        StringBuilder line = new StringBuilder();
        line.append(user);
        for (int i = 0; i < Math.min(3, allRecordList.size()); i ++) {
            String recommendWord = allRecordList.get(i);
            recommendWordCount.put(recommendWord, recommendWordCount.getOrDefault(recommendWord, 0)+1);
            line.append(" ").append(recommendWord);
        }
        recommendations.add(line.toString());
    }
    
    private void writeRecomsIntoFile(List<String> recommends) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("recommendations.txt"))) {
            for (String recommend : recommends) {
                writer.write(recommend+"\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int indexOfKeyword(String keyword) {
        for (int i = 0; i < ranking.size(); i ++) {
            if (ranking.get(i).getKeyword().equals(keyword))
                return i;
        }
        return -1;
    }
    
    public static void main(String[] args) {
        Main process = new Main();
        process.loadKeywordCounts();
        process.rankKeywords();
        process.calcRelations();
        process.calcRecom();
    }
}
