package algorithm.sort;

import utilities.ArrayUtils;

public class SelectionSort<T extends Comparable<T>> implements Sort<T>{
    
    @Override
    public void sort(T[] array, int begin, int end) {
        for (int i = begin; i < end; i ++) {
            int minIndex = i;
            // 每次找最小的元素
            for (int j = i + 1; j <= end; j ++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            ArrayUtils.swap(array, minIndex, i);
        }
    }
}
