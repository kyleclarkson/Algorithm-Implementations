package SequenceAlignment;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

public class UnitTest {

    @Test
    public void test1() {

        String X = "stop";
        String Y = "top";

        SequenceAlignment seqAli = new SequenceAlignment(X, Y);
        // Set alphabet
        seqAli.setAlphabetMap(new ArrayList<Character>(
                Arrays.asList('s','t','o','p')
        ));

        // Set cost matrix
        float[][] costMatrix = {
                {0,2,2,2},
                {2,0,2,2},
                {2,2,0,2},
                {2,2,2,0}
        };
        double unMatchedCost = 1;
        seqAli.setCostMatrix(costMatrix, unMatchedCost);

        seqAli.computeAlignment();

        System.out.println(Arrays.deepToString(seqAli.dpMatrix));
        System.out.println(seqAli.getAlignment(X.length(), Y.length()));
        Assert.assertEquals(unMatchedCost, seqAli.dpMatrix[X.length()-1][Y.length()-1], 0.001);
    }
    @Test
    public void test2() {

        String X = "stop";
        String Y = "wess";

        SequenceAlignment seqAli = new SequenceAlignment(X, Y);
        // Set alphabet
        seqAli.setAlphabetMap(new ArrayList<Character>(
                Arrays.asList('e','o','p','s','t','w')
        ));

        // Set cost matrix
        float[][] costMatrix = {
                {0,100,100,100,100,100},
                {100,0,100,100,100,100},
                {100,100,0,100,100,100},
                {100,100,100,0,100,100},
                {100,100,100,100,0,100},
                {100,100,100,100,100,0}
        };
        double unMatchedCost = 1;
        seqAli.setCostMatrix(costMatrix, unMatchedCost);

        seqAli.computeAlignment();
        System.out.println(seqAli.getAlignment(X.length(),Y.length()));
        Assert.assertEquals(4*unMatchedCost, seqAli.dpMatrix[X.length()-1][Y.length()-1], 0.001);
    }

    @Test
    public void Test3() {
        String X = "mean";
        String Y = "name";

        SequenceAlignment seqAli = new SequenceAlignment(X, Y);
        // Set alphabet
        seqAli.setAlphabetMap(new ArrayList<Character>(
                Arrays.asList('a','e','m','n')
        ));

        float[][] costMatrix = {
                {0,1,3,3},
                {1,0,3,3},
                {3,3,0,1},
                {3,3,1,0}
        };
        double unMatchedCost = 2;
        seqAli.setCostMatrix(costMatrix, unMatchedCost);

        seqAli.computeAlignment();
        System.out.println(Arrays.deepToString(seqAli.dpMatrix));
        System.out.println(seqAli.getAlignment(X.length(), Y.length()));
    }

}
