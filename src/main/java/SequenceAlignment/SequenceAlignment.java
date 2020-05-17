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

    public SequenceAlignment(String X, String Y) {
        this.X = X;
        this.Y = Y;

        dpMatrix = new double[X.length()][Y.length()];
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
            for (int j=0; j< Y.length(); j++) {
                // The three cases:
                // 1) Match i and j:
                double case1 = getMatchCost(i, j) + dpMatrixHelper(i-1, j-1);

                // 2) Leave i unmatched.
                double case2 = unmatchedCost + dpMatrixHelper(i-1, j);

                // 3) Leave j unmatched.
                double case3 = unmatchedCost + dpMatrixHelper(i, j-1);

                // Set min cost as minimum of the three cases.
                dpMatrix[i][j] = Math.min(case1, Math.min(case2, case3));
            }
        }
    }

    private double getMatchCost(int i, int j) {
        char x_i = X.charAt(i);
        char y_j = Y.charAt(j);
        return costMatrix[alphabetMap.get(x_i)][alphabetMap.get(y_j)];
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
}
