package algorithm.sort;

import utilities.ArrayUtils;

public class BubbleSort<T extends Comparable<T>> implements Sort<T>{
    
    @Override
    public void sort(T[] array, int begin, int end) {
        for (int i = begin; i < end; i ++) {
            for (int j = begin; j < end - (i - begin); j ++) {
                if (array[j].compareTo(array[j+1]) > 0) {
                    ArrayUtils.swap(array, j, j + 1);
                }
            }
        }
    }
    
}
