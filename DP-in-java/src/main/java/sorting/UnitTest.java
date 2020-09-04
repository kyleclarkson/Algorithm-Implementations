package sorting;

import org.junit.Test;

import java.util.Arrays;

public class UnitTest {

    double[] array1 = new double[] {9,10,8,7,6,5,4,3,2,1};
    @Test
    public void Test1() {
        Sort.bubbleSort(array1);
        System.out.println(Arrays.toString(array1));
    }

    @Test
    public void Test2() {
        Sort.mergeSort(array1, 0, array1.length);
        System.out.println(Arrays.toString(array1));
    }

    @Test
    public void TestQuickSort() {
        Sort.quickSort(array1, 0, array1.length, "right-index");
        System.out.println(Arrays.toString(array1));
    }
}
