package BuySellDays;

import java.util.ArrayList;
import java.util.Random;

/**
 * Test the runtimes of each BuySell algorithm. Output runtimes to csv file.
 */
public class Runtimes {

    // Various number of days to test on.
//    static int[] TEST_SIZES = new int[] {10, 100, 1_000, 10_000, 100_000, 1_000_000};
    static int[] TEST_SIZES = new int[] {10, 100, 1_000};
    static int NUM_OF_TESTS = 10;
    // Prices will be in between these amounts.
    static double rangeMinValue = -10;
    static double rangeMaxValue = 200;
    static long startTime, endTime = 0;

    // Contains prices for testing.
    static ArrayList<Double[]>[][] testData;

    // Used for storing test results
    static StringBuilder[] sbArray;
    public static void main(String[] args) {
        testData = new ArrayList[TEST_SIZES.length][NUM_OF_TESTS];

        // Generate test data.
        for (int i=0; i<TEST_SIZES.length; i++) {
            for (int j=0; j<NUM_OF_TESTS; j++) {
                testData[i][j].add(getPrices(TEST_SIZES[i]));
            }
        }
        System.out.println("Generated test data ...");

    }

    public static Double[] getPrices(int numOfDays) {
        Random rd = new Random();
        Double[] prices = new Double[numOfDays];
        for (int i=0; i<prices.length; i++) {
            prices[i] = rangeMinValue + (rangeMaxValue - rangeMinValue) * rd.nextDouble();
        }
        return prices;
    }
}
