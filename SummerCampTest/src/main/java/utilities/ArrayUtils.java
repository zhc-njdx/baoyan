package utilities;

public class ArrayUtils {
    
    /**
     * 反转数组指定闭区间[begin, end]中的序列
     * @param array 数组
     * @param begin 开始位置
     * @param end   结束位置
     * @param <T>   数组中元素类型
     */
    public static <T> void reverseArray(T[] array, int begin, int end) {
        int middle = begin + (end - begin) / 2;
        for (int i = begin; i <= middle; i ++) {
            swap(array, i, end-(i-begin));
        }
    }
    
    /**
     * 判断一个数组是否为空
     * @param array T[]
     * @param <T> 元素类型
     * @return 空则true
     */
    public static <T> boolean isEmptyArray(T[] array) {
        return array == null || array.length == 0;
    }
    
    /**
     * 交换数组中两个下标的元素
     * @param array T[]
     * @param i index1
     * @param j index2
     * @param <T> 元素类型
     */
    public static <T> void swap(T[] array, int i, int j) {
        T tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
    
    /**
     * 打印数组元素，一个元素一行
     * @param array T[]
     * @param <T> 元素类型
     */
    public static <T> void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t.toString());
        }
    }
}
