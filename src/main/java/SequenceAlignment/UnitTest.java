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
        double[][] costMatrix = {
                {0,1,1,1},
                {1,0,1,1},
                {1,1,0,1},
                {1,1,1,0}
        };
        double unMatchedCost = 0.5;
        seqAli.setCostMatrix(costMatrix, unMatchedCost);

        seqAli.computeAlignment();
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
        double[][] costMatrix = {
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
        Assert.assertEquals(6*unMatchedCost, seqAli.dpMatrix[X.length()-1][Y.length()-1], 0.001);
    }
    // TODO create helper method that generates 'default' cost matrix for an alphabet. 
}
