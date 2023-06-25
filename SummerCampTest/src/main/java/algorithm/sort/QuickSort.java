package algorithm.sort;

public class QuickSort<T extends Comparable<T>> implements Sort<T> {
    @Override
    public void sort(T[] array, int begin, int end) {
        if (begin < end) {
            int pivot = partition(array, begin, end);
            sort(array, begin, pivot-1);
            sort(array, pivot+1, end);
        }
    }
    
    private int partition(T[] array, int begin, int end) {
        T pivot = array[end];
        int i = begin - 1;
        for (int j = begin; j <= end; j ++) {
            if (array[j].compareTo(pivot) <= 0) {
                i ++;
                swap(array, i, j);
            }
        }
        return i;
    }
}
