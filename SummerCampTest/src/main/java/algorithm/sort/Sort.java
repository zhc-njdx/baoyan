package algorithm.sort;

import utilities.ArrayUtils;

public interface Sort<T extends Comparable<T>> {
    default void execute(T[] array, int begin, int end) throws RangeException {
        if (ArrayUtils.isEmptyArray(array)) return;
        assertValidRange(begin, end);
        sort(array, begin, end);
    }
    
    void sort(T[] array, int begin, int end);
    
    default void assertValidRange(int begin, int end) throws RangeException {
        if (begin > end) {
            System.err.println("["+begin+", "+end+"] is illegal");
            throw new RangeException();
        }
    }
}
