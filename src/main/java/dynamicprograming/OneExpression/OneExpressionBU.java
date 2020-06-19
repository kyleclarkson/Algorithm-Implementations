package dynamicprograming.OneExpression;


/**
 * A bottom-up approach to the expression of a positive integer using only 1's.
 * See the 'OneExpression.java' file for an explanation of the subproblem structure of the problem.
 */
public class OneExpressionBU extends OneExpression{

    public OneExpressionBU() {
        super();
    }

    // Use bottom-up approach to build dpMatrix values.
    public int computeRecurrence(int m) {
        if (dpMatrix.length < m + 1) {
            this.copyArrayValues(m);
        }

        for (int j=1; j<=m; j ++) {
            // Check case where m = x * y
            int currentMinNumOfOnesMultiplication = j;

            // iterate over all divisors of m, upto root m.
            for(int i=2; i<Math.ceil(Math.sqrt(j)); i++) {
                if (j % i == 0) {
                    int newMinNumOfOnes = dpMatrix[j / i] + dpMatrix[i];
                    if (newMinNumOfOnes < currentMinNumOfOnesMultiplication) {
                        currentMinNumOfOnesMultiplication = newMinNumOfOnes;
                    }
                }
            }

            // Check case where m = x + y
            int currentMinNumOfOnesAddition = j;

            // iterate over all subtractions, up to m/2
            for(int i=1; i<Math.ceil(j / 2.0); i++) {
                int newMinNumOfOnes = dpMatrix[j - i] + dpMatrix[i];
                if(newMinNumOfOnes < currentMinNumOfOnesAddition) {
                    currentMinNumOfOnesAddition = newMinNumOfOnes;
                }
            }

            // Compare the two cases, storing the min of the two.
            if(currentMinNumOfOnesMultiplication < currentMinNumOfOnesAddition) {
                dpMatrix[j] = currentMinNumOfOnesMultiplication;
            } else {
                dpMatrix[j] = currentMinNumOfOnesAddition;
            }
        }
        return dpMatrix[m];
    }

    public static void main(String[] args) {
        OneExpressionBU obu = new OneExpressionBU();
        OneExpression o = new OneExpression();

        int m = 1_000_000;
        obu.computeRecurrence(m);
        System.out.println(obu.dpMatrix[m]);
        obu.printMatrixToFile("1000000.csv");
        System.out.println("1,000,000: "+ obu.computeExpression(m));
        System.out.println("333: " + obu.computeExpression(333));
        System.out.println("51,214: " + obu.computeExpression(51214));
        System.out.println("219,743: " + obu.computeExpression(219743));
    }
}
