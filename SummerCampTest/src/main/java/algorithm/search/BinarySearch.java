package algorithm.search;

import java.util.Arrays;

/**
 * 二分搜索 升序
 * 存储的是 index = [0, n-1]
 * 需要额外一个 index => Object 的映射
 */
public class BinarySearch {
    int n;
    int[] array;

    /**
     * 构造二分类
     * @param nums 数组
     * @param asc 所给数组是否升序
     */
    public BinarySearch(int[] nums, boolean asc) {
        n = nums.length;
        array = new int[n];
        System.arraycopy(nums, 0, array, 0, n);
        if (!asc) {
            Arrays.sort(array); // 快排=>升序
        }
    }

    public int find(int num) {
        int l = 0; int r = n-1;
        while(l <= r) {
            int m = l + (r - l) / 2;
            if (array[m] == num) return m;
            if (array[m] > num) r = m-1;
            else l = m+1;
        }
        return -1;
    }
}
