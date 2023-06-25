package algorithm.sort;

import utilities.Utility;

public interface Sort<T extends Comparable<T>> {
    default void execute(T[] array, int begin, int end) throws RangeException {
        if (Utility.isEmptyArray(array)) return;
        assertValidRange(begin, end);
        sort(array, begin, end);
    }
    
    void sort(T[] array, int begin, int end);
    
    default void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    
    default void assertValidRange(int begin, int end) throws RangeException {
        if (begin > end) {
            System.err.println("["+begin+", "+end+"] is illegal");
            throw new RangeException();
        }
    }
}
