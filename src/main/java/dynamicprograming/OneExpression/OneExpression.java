package dynamicprograming.OneExpression;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 *      One Expression:
 *
 *      Given a positive integer m we express m using the minimum number of '1's
 *      using only addition and multiplication of terms, where terms are embedded in
 *      parenthesis to ensure correct order of operations.
 *
 *      For each m we store the minimum number of ones needed in an array dpMatrox(m)
 *      with basecase m=1 -> dpMatrix(1) = 1.
 *
 *      We observe the following subproblem structure. For m > 1, m can be decomposed as the addition
 *      of two terms or the product of two terms. Each of these subproblems will contain a number of ones.
 *      The minimum number of ones needed can be found by minimizing over all ways of decomposing m.
 *
 *      1) m = x + y:
 *          dpMatrix(m) = min_i {dpMatrix(m-i) + dpMatrix(i)} where 1 <= i <= n/2
 *              (by symmetry we only need to check up to n/2)
 *
 *      2) m = (x)(y)
 *          dpMatrix(m) = min_i {dpMatrix(m/i) + dpMatrix(i)} where i divides m and i <= sqrt(m).
 *
 *      We use a memoization approach to compute dpMatrix(m)
 */
public class OneExpression {

    // Use array of length m + 1
    int[] dpMatrix;

    public OneExpression() {
        // New arrays are initialized with value 0.
        dpMatrix = new int[2];
        dpMatrix[1] = 1;
    }

    public int computeRecurrence(int m) {
        if (dpMatrix.length < m + 1) {
            // Create larger array, copy existing values.
            copyArrayValues(m);
        }

        if (dpMatrix[m] == 0 && m > 0) {

            // Check case where m = x * y
            int currentMinNumOfOnesMultiplication = m;

            // iterate over all divisors of m, upto root m.
            for(int i=2; i<Math.ceil(Math.sqrt(m)); i++) {
                if (m % i == 0) {
                    int newMinNumOfOnes = computeRecurrence(m / i) + computeRecurrence(i);
                    if (newMinNumOfOnes < currentMinNumOfOnesMultiplication) {
                        currentMinNumOfOnesMultiplication = newMinNumOfOnes;
                    }
                }
            }

            // Check case where m = x + y
            int currentMinNumOfOnesAddition = m;

            // iterate over all subtractions, up to m/2
            for(int i=1; i<Math.ceil(m / 2.0); i++) {
                int newMinNumOfOnes = computeRecurrence(m - i) + computeRecurrence(i);
                if(newMinNumOfOnes < currentMinNumOfOnesAddition) {
                    currentMinNumOfOnesAddition = newMinNumOfOnes;
                }
            }

            // Compare the two cases, storing the min of the two.
            if(currentMinNumOfOnesMultiplication < currentMinNumOfOnesAddition) {
                dpMatrix[m] = currentMinNumOfOnesMultiplication;
            } else {
                dpMatrix[m] = currentMinNumOfOnesAddition;
            }
        }
        return dpMatrix[m];
    }

    // Use values stored in dpMatrix to compute expression for m.
    public String computeExpression(int m) {
        // Ensure dpMatrix is populated.
        if (dpMatrix.length > m + 1) {
            computeRecurrence(m);
        }

        StringBuilder send = new StringBuilder();

        if (m == 1) {
            return "1";
        } else {
            // Check case where m = x * y
            int currentMinNumOfOnesMultiplication = m;
            int multiplicationIndex = 1;

            // iterate over all divisors of m, upto root m.
            for(int i=2; i<Math.ceil(Math.sqrt(m)); i++) {
                if (m % i == 0) {

                    int newMinNumOfOnes = computeRecurrence(m / i) + computeRecurrence(i);
                    if (newMinNumOfOnes < currentMinNumOfOnesMultiplication) {
                        multiplicationIndex = i;
                        currentMinNumOfOnesMultiplication = newMinNumOfOnes;
                    }
                }
            }

            // Check case where m = x + y
            int currentMinNumOfOnesAddition = m;
            int additionIndex = 1;

            // iterate over all subtractions, up to m/2
            for(int i=1; i<Math.ceil(m / 2.0); i++) {
                int newMinNumOfOnes = computeRecurrence(m - i) + computeRecurrence(i);
                if(newMinNumOfOnes < currentMinNumOfOnesAddition) {
                    additionIndex = i;
                    currentMinNumOfOnesAddition = newMinNumOfOnes;
                }
            }

            // Compare the two cases, adding to string appropriately.
            if(currentMinNumOfOnesMultiplication < currentMinNumOfOnesAddition) {
                send.append("("+computeExpression(m / multiplicationIndex)+")("+computeExpression(multiplicationIndex)+")");
            } else {
                send.append(computeExpression(m - additionIndex) + "+" + computeExpression(additionIndex));
            }
        }
        return send.toString();
    }

    // Print dpMatrix in csv format
    public void printMatrixToFile(String filepath) {

        try(PrintWriter writer = new PrintWriter(new File(filepath))) {

            StringBuilder builder = new StringBuilder();

            for(int i=1; i<dpMatrix.length; i++) {
                builder.append(i).append(",").append(dpMatrix[i]);
                builder.append("\n");
            }

            writer.write(builder.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void copyArrayValues(int m) {

        int[] newArray = new int[m+1];
        for (int i=0; i<dpMatrix.length; i++) {
            newArray[i] = dpMatrix[i];
        }
        dpMatrix = newArray;
    }

    public static void main(String[] args) {
        OneExpression o = new OneExpression();
        // Testing
        int m = 12;

        o.computeRecurrence(m);
        System.out.println(o.dpMatrix[m]);
//        o.printMatrixToFile(m+".csv");
        System.out.println(o.computeExpression(m));
        System.out.println(o.dpMatrix[6]);
        System.out.println(o.computeExpression(6));
    }
}
