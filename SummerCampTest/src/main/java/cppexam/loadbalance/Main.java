package cppexam.loadbalance;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String test = "/Users/zhenghanchao/zhc/baoyan/SummerCampTest/src/main/java/cppexam/loadbalance/test.txt";
        try (Scanner scanner = new Scanner(new File(test))) {
            int[] ints = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            int m = ints[0];
            int n = ints[1];
            int s = ints[2];
            Balance balance;
            if (s == Balance.STRATEGY_FOUR) {
                int[] weight = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
                balance = new Balance(n, weight);
            } else {
                balance = new Balance(n, s);
            }
            for (int i = 0; i < m; i ++) {
                String tag = scanner.nextLine();
                Request r = new Request(tag);
                balance.balance(r);
                System.out.println(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
