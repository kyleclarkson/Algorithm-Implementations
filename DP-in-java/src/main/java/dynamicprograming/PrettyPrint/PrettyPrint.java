package dynamicprograming.PrettyPrint;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class PrettyPrint {

    final double INF = Double.POSITIVE_INFINITY;

    // Store the slack of line text[i:j] inclusive.
    double[][] slack;
    double dpMatrix[];
    int dpMatrixIndices[];
    int lineLength;
    String[] text;

    public PrettyPrint(int lineLength, String[] text) {
        this.lineLength = lineLength;
        this.text = text;

        slack = new double[text.length][text.length];
        dpMatrix = new double[text.length+1];
        dpMatrixIndices = new int[text.length+1];
        dpMatrix[0] = 0;
        dpMatrixIndices[0] = 0;
    }

    public void computeSlackValues() {
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
                    slack[i][j] = Math.pow(lineLength - slack[i][j], 1);
                }
                j++;
            }
        }
    }

    /**
     * Compute values of dpMatrix.
     */
    public void computeOPT(){
        // Iterate to set values of dpMatrix
        for(int i=1; i<=text.length; i++) {
            double min = INF;
            int minIndex = -1;
            for(int j=0; j<i; j++) {
                if(slack[j][i-1] + dpMatrix[j] < min) {
                    minIndex = j;
                    min = slack[j][i-1] + dpMatrix[j];
                }
            }
            dpMatrix[i] = min;
            dpMatrixIndices[i] = minIndex;
        }
    }

    /**
     * Backtrack through dpMatrix to compute formatting of text.
     */
    public String getParagraph() {
        StringBuilder send = new StringBuilder();

        int startIndex = dpMatrixIndices[dpMatrixIndices.length-1];
        int endIndex = text.length-1;
        while(startIndex >= 0) {
            // Build line of text from startIndex to endIndex, inclusive.
            String line = getLine(startIndex, endIndex);
            send.append(line);
            send.append("\n");

            // Break from loop once start of string is reached.
            if (startIndex == 0)
                break;
            endIndex = startIndex - 1;
            startIndex = dpMatrixIndices[endIndex];

        }
        // Reverse text to ensure order is correct.
        return send.reverse().toString();
    }

    /**
     * Returns line of text, reversed. This reserve is 'undone' in getParagraph method.
     * This is done so that the stringbuilder appends lines to the front, instead of at
     * the back.
     */
    private String getLine(int startIndex, int endIndex) {
        StringBuilder send = new StringBuilder();

        for(int i=startIndex; i<=endIndex; i++) {
            send.append(text[i]);
            if (i != endIndex) {
                send.append(" ");
            }
        }
        // Reverse line
        return send.reverse().toString();
    }

    public static String[] readTextFromFile(String filepath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));

            List<String> lines = new ArrayList<String>();
            String line = null;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();

            return lines.toArray(new String[lines.size()]);

        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String[] text = PrettyPrint.readTextFromFile("src/main/java/dynamicprograming/PrettyPrint/test1.txt");

        PrettyPrint pp = new PrettyPrint(40, text);

        pp.computeSlackValues();
        pp.computeOPT();
        System.out.println(pp.dpMatrix[text.length]);
        System.out.println(pp.getParagraph());
    }
}