package algorithm.sort;

public class InsertionSort<T extends Comparable<T>> implements Sort<T>{
    /**
     * 算法原理：将数组分为已排序和未排序两部分，每次从未排序部分选择一个元素插入到已排序部分的合适位置。
     */
    @Override
    public void sort(T[] array, int begin, int end){
        for (int i = begin + 1; i <= end; i++) {
            T key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j].compareTo(key) > 0) {
                array[j+1] = array[j];
                j = j - 1;
            }
            array[j+1] = key;
        }
    }
}
