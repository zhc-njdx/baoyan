package algorithm.sort;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MergeSort<T extends Comparable<T>> implements Sort<T> {
    
    @Override
    public void sort(T[] array, int begin, int end) {
        if (begin < end) {
            int middle = begin + (end - begin) / 2;
            sort(array, begin, middle);
            sort(array, middle+1, end);
            merge(array, begin, middle, end);
        }
    }
    
    private void merge(T[] array, int begin, int middle, int end) {
        int leftLen = middle - begin + 1;
        int rightLen = end - middle;
        List<T> leftList = Arrays.stream(array, begin, middle + 1).collect(Collectors.toList());
        List<T> rightList = Arrays.stream(array, middle + 1, end + 1).collect(Collectors.toList());
        
        int i = 0;
        int j = 0;
        int k = begin;
        
        while (i < leftLen && j < rightLen) {
            if (leftList.get(i).compareTo(rightList.get(j)) < 0) {
                array[k] = leftList.get(i);
                i++;
            } else {
                array[k] = rightList.get(j);
                j++;
            }
            k++;
        }
        
        while (i < leftLen) {
            array[k] = leftList.get(i);
            k++;
            i++;
        }
        while (j < rightLen) {
            array[k] = rightList.get(j);
            k++;
            j++;
        }
    }
}
