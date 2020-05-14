package BuySellDays;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Test the runtimes of each BuySell algorithm. Output runtimes to csv file.
 */
public class Runtimes {

    // Various number of days to test on.
    static int[] TEST_SIZES = new int[] {10, 100, 1_000, 10_000, 100_000, 1_000_000};
//    static int[] TEST_SIZES = new int[] {10, 100, 1_000};
    static int NUM_OF_TESTS = 15;
    // Prices will be in between these amounts.
    static double rangeMinValue = -10;
    static double rangeMaxValue = 200;
    static long startTime, endTime = 0;

    // Contains prices for testing.
    static ArrayList<double[]>[][] testData;

    // Used for storing runtime of tests.
    static ArrayList<Double>[][] testResults;
    /*
        === Main Method ===
     */
    public static void main(String[] args) {

        // Generate test data.
        testData = new ArrayList[TEST_SIZES.length][NUM_OF_TESTS];
        for (int i=0; i<TEST_SIZES.length; i++) {
            for (int j=0; j<NUM_OF_TESTS; j++) {
                testData[i][j] = new ArrayList<>();
                testData[i][j].add(getPrices(TEST_SIZES[i]));
            }
        }
        System.out.println("Generated test data ...");

        // Initialize container for testResults. 3 'rows' for the 3 methods that are tested.
        testResults = new ArrayList[3][TEST_SIZES.length];

        // Run tests
        /*
            k=0 -> Naive Test
            k=1 -> DNC Test
            k=2 -> DP Test
         */
        BuySellDays bsd = new BuySellDays();
        for (int k=0; k<3; k++) {
            System.out.println("K="+k);
            for (int i=0; i<TEST_SIZES.length; i++) {
                System.out.println("Running test size: " + TEST_SIZES[i] +" ... ");
                testResults[k][i] = new ArrayList<>();
                for (int j=0; j<NUM_OF_TESTS; j++) {
                    System.out.println("test number: "+j);
                    for (int l=0; l<testData[i][j].size(); l++) {
                        double[] prices = testData[i][j].get(l);
                        bsd.setPrices(prices);
                        startTime = System.nanoTime();
                        switch (k) {
                            case 0:
                                bsd.Naive();
                                break;
                            case 1:
                                bsd.DNC();
                                break;
                            case 2:
                                bsd.DP();
                                break;
                        }
                        endTime = System.nanoTime();
                        double duration = (double)(endTime-startTime)/1_000_000;
                        System.out.println("k="+k+": Duration: " + duration);
                        testResults[k][i].add(duration);
                    }
                }
            }
        }

        // Print test results to csv file.
        for (int l=0; l<testResults.length; l++) {
            String fileName;
            if (l == 0)         fileName = "naive.csv";
            else if (l == 1)    fileName = "dnc.csv";
            else                fileName = "dp.csv";
            /*
                First row of file contains test sizes (header)
                Following rows (NUM_OF_TESTS) contain runtimes.
             */
            try (PrintWriter pw = new PrintWriter(new File("test_results/BuySell/"+fileName))) {

                StringBuilder sb = new StringBuilder();
                // Write  header
                for (int i=0;i<TEST_SIZES.length; i++) {
                    sb.append(TEST_SIZES[i]);
                    if (i < TEST_SIZES.length-1) {
                        sb.append(',');
                    }
                }
                sb.append("\n");

                // Write run times
                for (int i=0; i<NUM_OF_TESTS; i++) {
                    // For each row:
                    for(int j=0; j<TEST_SIZES.length; j++) {
                        // For each col:
                        ArrayList<Double> runtimes = testResults[l][j];
                        sb.append(runtimes.get(i));
                        if (j < TEST_SIZES.length - 1){
                            sb.append(",");
                        }
                    }
                    if (i < NUM_OF_TESTS-1) {
                        sb.append("\n");
                    }
                }

                pw.write(sb.toString());
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    protected static double[] getPrices(int numOfDays) {
        Random rd = new Random();
        double[] prices = new double[numOfDays];
        for (int i=0; i<prices.length; i++) {
            prices[i] = rangeMinValue + (rangeMaxValue - rangeMinValue) * rd.nextDouble();
        }
        return prices;
    }
}
