package sorting;

import java.util.Arrays;

public class Sort {

    public static void bubbleSort(double[] array) {
        for(int i=0; i<array.length; i++) {
            double min = array[i];
            int indexOfMin = i;
            for (int j=i+1; j<array.length; j++) {
                if (array[j] < min) {
                    min = array[j];
                    indexOfMin = j;
                }
            }
            // Swap entries
            array[indexOfMin] = array[i];
            array[i] = min;
        }
    }

    public static void mergeSort(double[] array, int leftIndex, int rightIndex) {
        // Base case array contains at most one element.
        if (rightIndex-leftIndex <= 1) {
            return;
        }
        // Array contains at at least two elements.
        // Split array, each part, then merge.
        else {
            int midIndex = (rightIndex + leftIndex) / 2;

            mergeSort(array, leftIndex, midIndex);
            mergeSort(array, midIndex, rightIndex);

            // Create temp arrays, merge together.
            double[] leftArray = new double[midIndex-leftIndex];
            double[] rightArray = new double[rightIndex-midIndex];

            for (int i=0; i < leftArray.length; i++) {
                leftArray[i] = array[leftIndex + i];
            }
            for (int i=0; i < rightArray.length; i++) {
                rightArray[i] = array[midIndex+i];
            }

            // Replace contents of array.
            double[] result = merge(leftArray, rightArray);
            for (int i=0; i < result.length; i++){
                array[leftIndex+i] = result[i];
            }
        }
    }

    /**
     * Merge two arrays by sorting elements.
     * @return The combination of the two input arrays.
     */
    public static double[] merge(double[] array1, double[] array2) {
        double[] result = new double[array1.length + array2.length];

        int index1 = 0, index2 = 0;
        do {
            if (array1[index1] < array2[index2]) {
                result[index1 + index2] = array1[index1];
                index1++;
            } else {
                result[index1 + index2] = array2[index2];
                index2++;
            }
        }
        // Add entries to result until at least one index passes through its array.
        while(index1 < array1.length && index2 < array2.length);

        // Only one of the following while loops will run.
        while (index1 < array1.length) {
            result[index1+index2] = array1[index1];
            index1++;
        }
        while (index2 < array2.length) {
            result[index1+index2] = array2[index2];
            index2++;
        }

        return result;
    }

}
