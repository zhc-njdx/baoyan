package cppexam.plantszombies;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    public static int plantCnt;
    public static int zombieCnt;
    public static boolean zombieIntoRoom = false;
    public static final String TEST_FILE = "D:\\study\\BaoYan\\baoyan\\SummerCampTest\\src\\main\\java\\cppexam\\plantszombies\\test2.txt";
    
    public void putPlantsAndZombies() {
        try(Scanner scanner = new Scanner(new File(TEST_FILE))){
            String[] counts = scanner.nextLine().split(" ");
            plantCnt = Integer.parseInt(counts[0]);
            zombieCnt = Integer.parseInt(counts[1]);
            for (int i = 0; i < plantCnt; i ++) {
                String[] s = scanner.nextLine().split(" ");
                int[] ints = Arrays.stream(s, 1, s.length).mapToInt(Integer::parseInt).toArray();
                new Plant(s[0], ints);
            }
            for (int i = 0; i < zombieCnt; i ++) {
                String[] s = scanner.nextLine().split(" ");
                final int[] ints = Arrays.stream(s).mapToInt(Integer::parseInt).toArray();
                new Zombie(ints);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void plantAttack() {
        for (int i = 0; i < Board.ROW; i ++) {
            for (int j = 0; j < Board.COL; j ++) {
                Grid g = Board.getGrid(i, j);
                if (g.hasPlant()) {
                    g.plant.attack();
                }
            }
        }
    }
    
    public void zombieMove() {
        for (int i = 0; i < Board.ROW; i ++) {
            for (int j = 0; j < Board.COL; j ++) {
                Grid g = Board.getGrid(i, j);
                if (g.hasZombie()) {
                    g.zombiesMove();
                }
            }
        }
    }
    
    public void zombieAttack() {
        for (int i = 0; i < Board.ROW; i ++) {
            for (int j = 0; j < Board.COL; j ++) {
                Grid g = Board.getGrid(i, j);
                if (g.hasZombie()) {
                    g.zombiesAttack();
                }
            }
        }
    }
    
    /**
     * 打印本轮信息，并判断游戏是否结束
     * @param round 轮次
     * @return 游戏是否结束
     */
    public boolean printRoundInfo(int round) {
        System.out.println(round + " " + plantCnt + " " + zombieCnt);
        if (zombieIntoRoom) {
            System.out.println("zombies win");
            return true;
        } else if (zombieCnt == 0) {
            System.out.println("plants win");
            return true;
        }
        return false;
    }
    
    public void begin() {
        putPlantsAndZombies();
        int round = 1;
        while (true) {
            plantAttack();
            zombieMove();
            zombieAttack();
            if (printRoundInfo(round)) {
                break;
            }
            round++;
        }
    }
}
