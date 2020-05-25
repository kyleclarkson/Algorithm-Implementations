import dynamicprogramming.BuySellDays.BuySellDays;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world");

        Random rd = new Random();
        int numOfDays = 1_000_000;
        double rangeMinValue = -10;
        double rangeMaxValue = 200;
        long startTime, endTime = 0;
        double[] prices = new double[numOfDays];

        for(int i=0; i<prices.length; i++) {
            prices[i] = rangeMinValue + (rangeMaxValue - rangeMinValue) * rd.nextDouble();
        }
        System.out.println("Finished generating data");
        BuySellDays bsd = new BuySellDays(prices);

        startTime = System.nanoTime();
        bsd.Naive();
        endTime = System.nanoTime();
        System.out.println("Naive duration: " + (double)(endTime-startTime)/1_000_000);

        startTime = System.nanoTime();
        bsd.DNC();
        endTime = System.nanoTime();
        System.out.println("DNC duration: " + (double)(endTime-startTime)/1_000_000);

        startTime = System.nanoTime();
        bsd.DP();
        endTime = System.nanoTime();
        System.out.println("DP duration: " + (double)(endTime-startTime)/1_000_000);

    }
}
