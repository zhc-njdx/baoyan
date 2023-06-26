package algorithm.sort;


public class SortTest {
    static Integer[] array = new Integer[]{9,8,7,6,5,4,3,2,1};
    public static void main(String[] args) {
        Sort<Integer> sort = new MergeSort<>();
        try{
            sort.execute(array, 0, array.length-1);
            printArray(array);
        } catch (RangeException e) {
            e.printStackTrace();
        }
    }
    
    public static void printArray(Integer[] a) {
        for (Integer integer : a) {
            System.out.println(integer);
        }
    }
}
