package dynamicprogramming.PrettyPrinting;


import java.util.Arrays;

/**
 * The <i>pretty printing</i> problem asks how should a sequence of text
 * be partitioned into a paragraph (lines of text) such that each line contains
 * at most 'L' characters and each line is 'roughly' the same length. This results
 * in each line starting at the same left column, and ending roughly at the
 * same right column.
 * <p>
 * Suppose the text consists of n words. Each word w_i consists of l_i characters
 * (i.e. has length l_i.) First observe that when we partition the text into lines
 * the order of the text must remain the same. This results in O(n^2) partitions of
 * the text.
 * <p>
 * We assume that the text given is punctuated, but does not contain spaces. Thus for each
 * word placed into a line (except the last word of the line) we add a trailing space character.
 * <p>
 * For each partition on a line, if the length of the partition is larger than lineLength, such a
 * partition is infeasible and has cost positive infinite. Otherwise we define the cost of this partition
 * as lineLength - length of partition. Denote this <i>quantity</i> the slack of the partition.
 * <p>
 * Finally we observe this problem has optimal substructure. Suppose the optimal solution has k lines with
 * some cost and the last line contains to partition text[j:n]. Then the optimal cost for the k lines is
 * to partition at index j that minimizes:
 * cost(text[j:n]) + OPT(text[1:j-1]) on k-1 lines.
 * where OPT(i) is the optimal cost of the way to partition the words text[1:i].
 */
public class PrettyPrinting {

    final double INF = Double.POSITIVE_INFINITY;

    // Store the slack of line text[i:j] inclusive.
    double[][] slack;
    int lineLength;
    String[] text;

    public PrettyPrinting(int lineLength, String[] text) {
        this.lineLength = lineLength;
        this.text = text;

        slack = new double[text.length][text.length];
    }

    public void computeSlackValues() {
        // Test again.
        // Compute cost of each partition
        for (int i = 0; i < text.length; i++) {
            double lengthOfPartition = 0;
            int j = i;
            while (j < text.length) {
                // This difference is the number of spaces needed by the partition.
                lengthOfPartition += text[j].length();
                slack[i][j] = lengthOfPartition;
                // Add number of spaces this partition will need
                slack[i][j] += j - i;

                // If the slack exceeds the line limit, set to infinity as it is infeasible.
                // Otherwise store slack.
                if (lineLength < slack[i][j]) {
                    slack[i][j] = INF;
                } else {
                    slack[i][j] = lineLength - slack[i][j];
                }
                j++;
            }
        }
    }

    public static void main(String[] args) {

    }
}