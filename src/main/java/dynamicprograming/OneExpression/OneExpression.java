package dynamicprograming.OneExpression;


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
    private int[] dpMatrix;

    public OneExpression() {
        dpMatrix = new int[1];
    }

    public void computeRecurrence(int m) {
        if (dpMatrix.length > m + 1) {
            // Create larger array, copy existing values.
            copyArrayValues(m);
        }

        // TODO implement algorithm to compute matrix values.

    }

    // Use values stored in dpMatrix to compute expression for m.
    public String computeExpression(int m) {
        if (dpMatrix.length > m + 1) {
            computeRecurrence(m);
        }

        // TODO implement
        return null;
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
        o.computeRecurrence(1);
    }
}
