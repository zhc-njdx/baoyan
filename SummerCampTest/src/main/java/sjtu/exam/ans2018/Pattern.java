package sjtu.exam.ans2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;

/**
 * @program: HuhuSearch
 * @description:
 * @author: Annntn
 * @create: 2018-06-24 21:22
 **/

class Node {
    String x;
    String y;
    String z;

    Node(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return x.equals(node.x) &&
                y.equals(node.y) &&
                z.equals(node.z);
    }

    @Override
    public int hashCode() {

        return Objects.hash(x, y, z);
    }
    
    @Override
    public String toString() {
        return "("+x+", "+y+", "+z+")";
    }
}

public class Pattern {
    int[][] gap = {{
            0, 75, 150
    }, {
            150, 225, 300
    }, {
            300, 375, 450
    }, {
            450, 525, 600
    }};

    Pattern(String filePath) {
        this.filePath = filePath;
    }

    String filePath = "";
    int maxn = 601;
    Node[] arr = new Node[maxn];

    public void check() throws FileNotFoundException {
        Scanner cin = new Scanner(new File(filePath));
        for (int i = 1; i < maxn; i++) {
            String line = cin.nextLine();
            String[] temp = line.split(",");
            arr[i] = new Node(temp[0], temp[1], temp[2]);
        }
        System.out.println(arr[599].x);
        for (int i = 0; i < gap.length; i++) {
            int start = gap[i][0];
            int me = gap[i][1];
            int end = gap[i][2];
            checksub(start,me,end);
            resub(start,me,end);
        }

    }
    public ArrayList<int[][]> result = new ArrayList<>();
    public ArrayList<int[][]> result1 = new ArrayList<>();
    int W=1;
    public void checksub(int start,int me,int end){
        int N=76;
        int[][] H = new int[76][76];
        for (int i = start+1; i <= me; i++) {
            for (int j = me+1; j <= end; j++) {
                int ii=i-start;
                int jj=j-me;
                H[ii][jj]=Math.max(
                        H[ii-1][jj-1]+S(arr[i],arr[j]),
                        Math.max(
                                Math.max(H[ii-1][jj]-W,H[ii][jj-1]-W),0));
            }
        }
        if (start == 0) {
            for (int i = 1; i <= 75; i ++) {
                System.out.println(arr[i] + " " + arr[i+75]);
            }
            for (int[] ints : H) {
                for (int anInt : ints) {
                    System.out.print(anInt);
                }
                System.out.println();
            }
        }
        result.add(H);

    }
    public void resub(int start,int me,int end){
        int N=76;
        int[][] H = new int[77][77];
        for (int i = start+1; i <= me; i++) {
            for (int j = end; j >= me+1; j--) {

                int ii=i-start;
                int jj=j-me;
                H[ii][jj]=Math.max(
                        H[ii-1][jj+1]+S(arr[i],arr[j]),
                        Math.max(
                                Math.max(H[ii-1][jj]-W,H[ii][jj+1]-W),0));
            }
        }
        result1.add(H);
    }
    public int S(Node a,Node b){
        if (a.equals(b)){
            return 3;
        }
        else {
            return -1;
        }
    }
    public void print(){
        for (int i = 0; i < result1.get(2).length; i++) {
            for (int j = 0; j < result1.get(2)[0].length; j++) {
                System.out.print(result1.get(2)[i][j]);
            }
            System.out.println();
        }
    }

}
