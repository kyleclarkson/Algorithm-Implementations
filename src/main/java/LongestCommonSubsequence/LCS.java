package LongestCommonSubsequence;

/**
 *  A <i>subsequence</i> of a string X[1:n] is a string of the form x_i_1,x_i_2, ..., x_i_k
 *  where for each i, i_j < i_{j+1}.
 *
 *  The <i>Longest Common Subsequence Problem</i> is given two strings, what is the longest
 *  common subsequence to both of the strings.
 *
 *  We can construct a DP solution to this problem as follows. Let X[1:m], Y[1:n] be the input
 *  strings, and let dpMatrix[i,j] store the length of the LCS of X[1:i] and Y[1:j]. Observe we
 *  have the follow recurrence relationship.
 *
 *      If x_i == y_j (i.e. they are the same) then the LCS will include these characters. Thus
 *          L[i,j] = 1 + L[i-1, j-1]
 *      Otherwise if they are not the same, we ask for the LCS value (what is store in dpMatrix)
 *      of either discarding x_i or y_j. We then take the max over the two possibilities. That is,
 *      If x_i != y_j, then
 *          L[i,j] = max { L[i-1,j], L[i, j-1] }
 */
public class LCS {

    // The input strings.
    String X, Y;

    // The dpMatrix that stores lengths of LCS.
    int[][] dpMatrix;


    public LCS(String X_, String Y_) {
        this.X = X_;
        this.Y = Y_;

        // Set matrix to be of size one larger to handle base case.
        dpMatrix = new int[X.length()+1][Y.length()+1];

        // Set base cases
        for (int j=0; j<dpMatrix[0].length; j++){
            dpMatrix[0][j] = 0;
        }
        for (int i=0; i<dpMatrix.length; i++){
            dpMatrix[i][0] = 0;
        }
    }

    /**
     * Compute values of dpMatrix
     */
    public void computeLCS() {
        for(int i=1; i<=X.length(); i++) {
            for (int j=1; j<=Y.length(); j++) {

                if (X.charAt(i-1) == Y.charAt(j-1)) {
                    dpMatrix[i][j] = 1 + dpMatrix[i-1][j-1];
                } else {
                    dpMatrix[i][j] = Math.max(dpMatrix[i-1][j], dpMatrix[i][j-1]);
                }
            }
        }
    }

    public int getLCSLength() {
        return dpMatrix[X.length()][Y.length()];
    }
}
