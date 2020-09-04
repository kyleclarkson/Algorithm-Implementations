package dynamicprograming.LongestCommonSubsequence;

import org.junit.Assert;
import org.junit.Test;

public class UnitTest {

    @Test
    public void test1(){
        String X = "CGATAATTGAGA";
        String Y = "GTTCCTAATA";

        LCS lcs = new LCS(X, Y);

        lcs.computeLCS();

        Assert.assertEquals(6, lcs.getLCSLength());
    }
}
