package dynamicprogramming.SequenceAlignment;


import java.util.HashMap;
import java.util.List;

/**
 * SequenceAlignmentLinearSpace:
 *      An improvement on the dynamicprogramming.SequenceAlignment, where only linear space
 *      (in terms of the lengths of the two strings) is needed.
 *
 *      The improvement is possible by observing only two columns (assuming number
 *      of rows is less than number of columns) of the dpMatrix are needed to compute
 *      the optimal alignment given the recursion.
 */
public class SequenceAlignmentLinearSpace {

    // Map alphabet characters to indices of cost matrix.
    // For N length alphabet, each unique character is mapped to a unique value
    // in 0:N-1.
    private HashMap<Character, Integer> alphabetMap;
    // The cost matrix for matching characters with one another.
    // For an N length alphabet, the cost matrix has size NxN.
    float[][] costMatrix;
    // delta: The cost for aligning a character with no character of the alphabet.
    float unmatchedCost;

    // Assume X is shorter of two strings.
    String X,Y;

    float[][] dpMatrix;

    public SequenceAlignmentLinearSpace(String X_, String Y_) {
        // Set X string to be of shorter length
        if( X_.length() <= Y_.length()){
            this.X = X_;
            this.Y = Y_;
        } else {
            this.X = Y_;
            this.Y = X_;
        }

        dpMatrix = new float[X.length()+1][2];
    }

    /**
     * Create a mapping from alphabet characters to indices of cost matrix.
     * @param alphabet Contains a list of all characters in alphabet.
     *                 The order of character will need to match how the
     *                 cost matrix is initialized.
     */
    public void setAlphabetMap (List<Character> alphabet) {
        int alphabetSize = alphabet.size();
        alphabetMap = new HashMap<>(alphabetSize);
        for (int i=0; i< alphabetSize; i++) {
            alphabetMap.put(alphabet.get(i), i);
        }
    }

    public void setCostMatrix(float[][] costMatrix, double unmatchedCost) {
        this.costMatrix = costMatrix;
        this.unmatchedCost = (float)unmatchedCost;

        // Set base case values of dpMatrix
        for (int i=0; i<dpMatrix.length; i++){
            dpMatrix[i][0] = i*(float)unmatchedCost;
        }
    }

    /**
     * Populate the values of the dpMatrix using the recurrence.
     */
    public void computeAlignment() {
        for (int j=1; j<=Y.length(); j++) {
            dpMatrix[0][1] = j*unmatchedCost;
            for(int i=1; i<=X.length(); i++) {
                // The three cases:
                // 1) Match i and j:
                float case1 = getMatchCost(i, j) + dpMatrixHelper(i-1, 0);

                // 2) Leave i unmatched.
                float case2 = unmatchedCost + dpMatrixHelper(i-1, 1);

                // 3) Leave j unmatched.
                float case3 = unmatchedCost + dpMatrixHelper(i, 0);

                // Set min cost as minimum of the three cases.
                dpMatrix[i][1] = Math.min(case1, Math.min(case2, case3));
            }
            // Shift columns over if needed.
            if(j != Y.length()){
                for (int i=0; i<=X.length(); i++){
                    dpMatrix[i][0] = dpMatrix[i][1];
                }
            }
        }
    }

    public float getOptimalAlignmentCost(){
        return dpMatrix[X.length()][1];
    }

    private float getMatchCost(int i, int j) {
        char x_i = X.charAt(i-1);
        char y_j = Y.charAt(j-1);
        return costMatrix[alphabetMap.get(x_i)][alphabetMap.get(y_j)];
    }

    /**
     * Handles index out of bounds errors. If i is less than 0, the substring of
     * X is empty; it is being matched against (j+1) remaining characters in the
     * substring of Y.
     * @return The cost associated indices i,j.
     */
    private float dpMatrixHelper(int i, int j) {
        {
            return dpMatrix[i][j];
        }
    }
}
