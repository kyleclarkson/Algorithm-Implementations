package SequenceAlignment;

import java.util.HashMap;
import java.util.List;

/**
 *  SequenceAlignment is:
 *      Given two strings X and Y of m and n characters over an alphabet,
 *      and a cost matrix for matching each character to another character
 *      of the alphabet, what is an alignment of X with Y such that the alignment
 *      results in minimum cost as described by matrix?
 *
 *      The following must be true in the optimal solution. Either the last characters are
 *      matched or not. That is,
 *      either
 *          x_m and y_n are matched,
 *      or
 *          x_m is unmatched <i>or</i> y_n is unmatched.
 *
 *      This suggests a DP solution, where we consider the subproblem of matching fro i=[1:n]
 *      and j=[1:m]. Either align (i,j) then look up optimal way to match substrings X[1:i-1]
 *      and Y[1:j-1], or leave i unmatched (X[1:i-1],Y[1:j]) or leave j unmatched (X[1:i], Y[1:j-1]).
 *
 *      For each of the three decisions we pay the appropriate cost, then minimize over all three.
 *
 */
public class SequenceAlignment {

    // Map alphabet characters to indices of cost matrix.
    // For N length alphabet, each unique character is mapped to a unique value
    // in 0:N-1.
    private HashMap<Character, Integer> alphabetMap;
    // The cost matrix for matching characters with one another.
    // For an N length alphabet, the cost matrix has size NxN.
    double[][] costMatrix;
    // delta: The cost for aligning a character with no character of the alphabet.
    double unmatchedCost;

    String X,Y;

    // The matrix that contains the recursive DP values. Using a bottom-up
    // approach we populate the matrix from [0,0] to [M,N]
    double[][] dpMatrix;

    // A matrix which tracks how values of dpMatrix are set.
    BacktrackIndices[][] backtrackIndices;

    public SequenceAlignment(String X, String Y) {
        this.X = X;
        this.Y = Y;

        dpMatrix = new double[X.length()][Y.length()];
        backtrackIndices = new BacktrackIndices[X.length()+1][Y.length()+1];
        for(int i=0; i<X.length()+1; i++) {
            for(int j=0; j<Y.length()+1; j++) {
                backtrackIndices[i][j] = new BacktrackIndices();
            }
        }
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

    public void setCostMatrix(double[][] costMatrix, double unmatchedCost) {
        this.costMatrix = costMatrix;
        this.unmatchedCost = unmatchedCost;
    }

    /**
     * Populate the values of the dpMatrix using the recurrence.
     */
    public void computeAlignment() {
        for(int i=0; i<X.length(); i++) {
            for (int j=0; j<Y.length(); j++) {
                // The three cases:
                // 1) Match i and j:
                double case1 = getMatchCost(i, j) + dpMatrixHelper(i-1, j-1);

                // 2) Leave i unmatched.
                double case2 = getMatchCost(i, j) + dpMatrixHelper(i-1, j);

                // 3) Leave j unmatched.
                double case3 = getMatchCost(i, j) + dpMatrixHelper(i, j-1);

                // Set min cost as minimum of the three cases.
                dpMatrix[i][j] = Math.min(case1, Math.min(case2, case3));

                // Set indices for backtracking.
                if (dpMatrix[i][j] == case1){
                    backtrackIndices[i][j].setIndices(i-1,j-1);
                } else if (dpMatrix[i][j] == case2) {
                    backtrackIndices[i][j].setIndices(i-1, j);
                } else {
                    backtrackIndices[i][j].setIndices(i, j-1);
                }
            }
        }
    }

    /**
     * Returns the alignment corresponding to dpMatrix[i][j]. String returned is of format:
     *  X alignment \n
     *  Y alignment
     *
     * where '-' indicates gaps.
     */
    public String getAlignment(int i_, int j_) {
        StringBuilder XBuilder = new StringBuilder();
        StringBuilder YBuilder = new StringBuilder();

        int i = i_;
        int j = j_;
        while (i > 0 || j > 0) {
            int[] indices = backtrackIndices[i][j].getIndices();
            // value comes from i-1, j-1
            if(i-1 == indices[0] && j-1 == indices[1]) {
                XBuilder.append(X.charAt(i-1));
                YBuilder.append(Y.charAt(j-1));
                i--;
                j--;
            }
            // value comes from i-1.
            else if(i-1 == indices[0] && j == indices[1]) {
                YBuilder.append("-");
                XBuilder.append(X.charAt(j-1));
                i--;
            }
            // value comes from j-1
            else {
                YBuilder.append(Y.charAt(i-1));
                XBuilder.append("-");
                j--;
            }
        }

        return XBuilder.reverse().toString() + "\n" + YBuilder.reverse().toString();
    }

    private double getMatchCost(int i, int j) {
        if (i<0){
            return unmatchedCost;
        } else if (j < 0 ){
            return unmatchedCost;
        } else {
            char x_i = X.charAt(i);
            char y_j = Y.charAt(j);
            return costMatrix[alphabetMap.get(x_i)][alphabetMap.get(y_j)];
        }
    }

    /**
     * Handles index out of bounds errors. If i is less than 0, the substring of
     * X is empty; it is being matched against (j+1) remaining characters in the
     * substring of Y.
     * @return The cost associated indices i,j.
     */
    private double dpMatrixHelper(int i, int j) {
        if (i < 0) {
            return (j+1) * unmatchedCost;
        }
        else if (j < 0){
            return (i+1) * unmatchedCost;
        }
        else {
            return dpMatrix[i][j];
        }
    }

    /**
     * A helper class for backtracking through the dpMatrix values.
     * For each entry of dpMatrix(i,j), this class stores what indices resulted in
     * the entry value.
     */
    class BacktrackIndices {

        int i,j;
        public BacktrackIndices() {

        }

        public void setIndices(int i, int j){
            this.i = i;
            this.j = j;
        }

        public int[] getIndices(){
            return new int[]{this.i, this.j};
        }
    }
}
