package sjtu.q2020;

import utilities.FileIOs;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static final String DIR = "/Users/zhenghanchao/zhc/postgraduate-recommendation/assets/sjtu-se/2020机考/small-case/";
    public static final String LOG_FORMAT = "keywords-%d.log";
    private final Map<String, List<Record>> userKeywordCounts; // user -> record[]
    private List<Record> ranking; // record
    Map<Integer, String> idx2uid;
    private List<Relation> allRelations;
    
    public Main() {
        userKeywordCounts = new HashMap<>();
    }
    
    /**
     * 步骤1: 按照规定格式读取日志文件，统计日志文件中出现的每个用户搜索过的每个词的次数
     */
    public void loadKeywordCounts() {
        readLogs();
    }
    
    private void readLogs() {
        int totalLines = 0;
        try(Scanner in = new Scanner(System.in)){
            int n = in.nextInt();
            for (int i = 1; i <= n; i ++) {
                String file = String.format(DIR+LOG_FORMAT, i);
                totalLines += readSingleLog(file);
                System.out.println(totalLines + " " + userKeywordCounts.size());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private int readSingleLog(String file) {
        int lineCnt = 0;
        List<String> content = FileIOs.readFileByBuffer(file);
        for (String s : content) {
            String[] split = s.split(" ");
            String uid = split[0];
            String keyword = split[1];
            putIntoMap(uid, keyword);
            lineCnt++;
        }
        return lineCnt;
    }

    private void putIntoMap(String uid, String keyword) {
        if (userKeywordCounts.containsKey(uid)) { // 该用户已存在
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
        } else { // 该用户还不存在
            List<Record> records = new ArrayList<>();
            records.add(new Record(keyword));
            userKeywordCounts.put(uid, records);
        }
    }

    /**
     * 步骤2: 统计每个关键词被所有用户搜索的总次数
     */
    public void rankKeywords() {
        // 建立所有的keyword->出现总次数的映射
        ranking = userKeywordCounts.entrySet()
                .stream()
                .flatMap(entry -> entry.getValue().stream())
                .collect(Collectors.groupingBy(Record::getKeyword, Collectors.summingInt(Record::getCount)))
                .entrySet().stream()
                .map(entry -> new Record(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        ranking.sort(Comparator.reverseOrder()); // 排序是持久化的
        ranking.stream().limit(5).forEach(System.out::println);
    }

    /**
     * 步骤3: 计算两个用户之间的搜索关联度
     */
    public void calcRelations() {
        idx2uid = new HashMap<>();
        int idx = 0;
        for (Map.Entry<String, List<Record>> entry : userKeywordCounts.entrySet()) {
            idx2uid.put(idx, entry.getKey());
            idx ++;
        }
        int[][] relations = new int[idx][idx];
        allRelations = new ArrayList<>();
        for (int i = 0; i < idx; i ++) {
            for (int j = 0; j < idx; j ++) {
                String user1 = idx2uid.get(i);
                String user2 = idx2uid.get(j);
                relations[i][j] = calc(user1, user2);
                if (j > i) allRelations.add(new Relation(user1, user2, relations[i][j]));
            }
        }
        allRelations.stream().sorted(Comparator.reverseOrder()).limit(1).forEach(System.out::println);
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

    /**
     * 步骤4: 为每个用户提供关键词推荐
     */
    public void calcRecom() {
        Map<String, Integer> allRecommendations = new HashMap<>();
        idx2uid.forEach((idx, uid) -> {
            List<String> recommendations =
                    allRelations.stream()
                    .filter(r -> r.bigIdUser.equals(uid) || r.smallIdUser.equals(uid))
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .flatMap(r -> userKeywordCounts.get(r.findAnotherUser(uid)).stream())
                    .map(Record::getKeyword)
                    .distinct() // 去掉重复的元素
                    .filter(k -> !userKeywordCounts.get(uid).stream().map(Record::getKeyword).collect(Collectors.toList()).contains(k))
                    .sorted((k1, k2) -> indexOfKeyword(k1) - indexOfKeyword(k2))
                    .limit(3)
                    .collect(Collectors.toList());
            recommendations.forEach(r -> allRecommendations.put(r, allRecommendations.getOrDefault(r, 0)+1));
            String recommendationLine = recommendations.stream().reduce(uid, (r, r0) -> r + " " + r0);
            FileIOs.writeFile("recommendations.txt", true, recommendationLine);
        });
        allRecommendations.entrySet().stream().sorted((e1, e2) -> {
            if (e1.getValue().compareTo(e2.getValue())==0) return e1.getKey().compareTo(e2.getKey());
            return e2.getValue().compareTo(e1.getValue());
        }).limit(1).forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
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
