package sjtu.exam.q2018;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Check {
    List<Data> x;
    List<Data> y;
    int[][] result; // 相似度矩阵结果
    
    /**
     *
     * @param data 数据序列 需要拆分为相同的两个序列 一个作为x坐标 一个作为y坐标
     * @param isReversal 是否需要逆序
     */
    public Check(List<Data> data, boolean isReversal) {
        int len = data.size();
        x = new ArrayList<>();
        for (int i = 0; i < len/2; i ++) {
            x.add(data.get(i));
        }
        y = new ArrayList<>();
        for (int j = len/2; j < len; j ++) {
            y.add(data.get(j));
        }
        if (isReversal) {
            Collections.reverse(y);
        }
        result = new int[len/2+1][len/2+1];
    }
    
    /**
     * 计算相似度矩阵
     */
    public void check() {
        int len = x.size();
        for (int i = 0; i <= len; i ++) {
            for (int j = 0; j <= len; j ++) {
                result[i][j] = H(i, j);
            }
        }
    }
    
    private int S(Data d1, Data d2) {
        if (d1.equals(d2)) return 3;
        else return -1;
    }
    
    private static final int W = 1;
    
    private int H(int i, int j) {
        if (i == 0 || j == 0) return 0; // 初始情况
        return max(
            result[i-1][j-1]+S(x.get(i-1), y.get(j-1)), result[i-1][j]-W, result[i][j-1]-W, 0
        );
    }
    
    private int max(int a, int b, int c, int d) {
        return Math.max(a, Math.max(b, Math.max(c, d)));
    }
}
