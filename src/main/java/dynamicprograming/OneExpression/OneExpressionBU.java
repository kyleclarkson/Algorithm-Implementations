package dynamicprograming.OneExpression;


import java.util.Arrays;

/**
 * A bottom-up approach to the expression of a positive integer using only 1's.
 * See the 'OneExpression.java' file for an explanation of the subproblem structure of the problem.
 */
public class OneExpressionBU {

    DPWrapper[] dpMatrix;

    public OneExpressionBU() {

        dpMatrix = new DPWrapper[2];
        dpMatrix[0] = new DPWrapper(0, "", 0);
        dpMatrix[1] = new DPWrapper(1, "1", 0);
    }

    // Use bottom-up approach to build dpMatrix values.
    public int computeRecurrence(int m) {
        if (dpMatrix.length < m + 1) {
            this.copyArrayValues(m);
        }

        for (int j=2; j<=m; j ++) {
            // Check case where m = x * y
            int currentMinNumOfOnesMultiplication = j;
            int idxCurrentMinNumOfOnesMultiplication = 0;

            // iterate over all divisors of m, upto root m.
            for(int i=2; i<Math.ceil(Math.sqrt(j)); i++) {
                if (j % i == 0) {
                    int newMinNumOfOnes = dpMatrix[j / i].getInteger() + dpMatrix[i].getInteger();
                    if (newMinNumOfOnes < currentMinNumOfOnesMultiplication) {
                        currentMinNumOfOnesMultiplication = newMinNumOfOnes;
                        idxCurrentMinNumOfOnesMultiplication = i;
                    }
                }
            }

            // Check case where m = x + y
            int currentMinNumOfOnesAddition = j;
            int idxCurrentMinNumOfOnesAddition = 1;

            // iterate over all subtractions, up to m/2
            for(int i=1; i<Math.ceil(j / 2.0); i++) {
                int newMinNumOfOnes = dpMatrix[j - i].getInteger() + dpMatrix[i].getInteger();
                if(newMinNumOfOnes < currentMinNumOfOnesAddition) {
                    currentMinNumOfOnesAddition = newMinNumOfOnes;
                    idxCurrentMinNumOfOnesAddition = i;
                }
            }

            // Compare the two cases, storing the min of the two.
            if(currentMinNumOfOnesMultiplication < currentMinNumOfOnesAddition) {
                dpMatrix[j].setInteger(currentMinNumOfOnesMultiplication);
                dpMatrix[j].setNumOfOperations(dpMatrix[j / idxCurrentMinNumOfOnesMultiplication].getNumOfOperations()
                        + dpMatrix[idxCurrentMinNumOfOnesMultiplication].getNumOfOperations()
                        + 1
                        );

                String rep = "(".concat(dpMatrix[j / idxCurrentMinNumOfOnesMultiplication].getRepresentation()
                        .concat(")(")
                        .concat(dpMatrix[idxCurrentMinNumOfOnesMultiplication].getRepresentation())
                        .concat(")"));
                dpMatrix[j].setRepresentation(rep);
            } else {
                dpMatrix[j].setInteger(currentMinNumOfOnesAddition);
                dpMatrix[j].setNumOfOperations(
                        dpMatrix[j - idxCurrentMinNumOfOnesAddition].getNumOfOperations() +
                                dpMatrix[idxCurrentMinNumOfOnesAddition].getNumOfOperations() +
                                1
                );
                String rep = dpMatrix[j - idxCurrentMinNumOfOnesAddition].getRepresentation()
                        .concat("+")
                        .concat(dpMatrix[idxCurrentMinNumOfOnesAddition].getRepresentation());
                dpMatrix[j].setRepresentation(rep);
            }
        }
        return dpMatrix[m].getInteger();
    }

    public String getRepresentation(int m) {
        return dpMatrix[m].getRepresentation();
    }

    protected void copyArrayValues(int m) {

        DPWrapper[] newDpMatrix = new DPWrapper[m+1];

        for(int i=0; i<dpMatrix.length; i++) {
            newDpMatrix[i] = new DPWrapper(dpMatrix[i]);
        }

        for (int i=dpMatrix.length; i<newDpMatrix.length; i++) {
            newDpMatrix[i] = new DPWrapper();
        }

        dpMatrix = newDpMatrix;
    }



    public static void main(String[] args) {
        OneExpressionBU obu = new OneExpressionBU();

        int m = 20;
        obu.computeRecurrence(m);
        System.out.println(Arrays.toString(obu.dpMatrix));
    }
}
