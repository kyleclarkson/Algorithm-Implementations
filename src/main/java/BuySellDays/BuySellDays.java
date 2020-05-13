package BuySellDays;

/**
 *  The Buy-Sell-Days is:
 *      Given a stock's price over an n day period, on what day should an individual buy and sell
 *      stock to maximize revenue (max_rev = sell_price - buy_price)? The individual buys/sells once.
 *
 *      A solution to this problem essentially asks on what day i to buy is the price minimum, and on what day j, such that
 *      i <= j, to sell is the price maximum? If i == j, the revenue is 0. Thus max_rev >= 0.
 *      Note: If max_rev == 0, solution with be {0, 0}
 *
 *  The following includes several algorithms to solve this problem, each with its own runtime.
 *  Some approaches emphasis divide-and-conquer techniques as well as dynamic programming techniques.
 */
public class BuySellDays {

    private double[] prices;

    public BuySellDays() {

    }
    public BuySellDays(double[] prices) {
        this.prices = prices;
    }

    public void setPrices(double[] prices) {
        this.prices = prices;
    }

    /**
     * A Naive implementation that tries each day to buy, then each day after to sell.
     * Results in O(n^2) runtime.
     * @return An int 1x2 array. First and second values indicating indices to buy and sell.
     */
    public int[] Naive() {
        // A 1x2 array. First value is buy day, second is sell day.
        int[] result = new int[2];

        double currentMaxRev = Double.NEGATIVE_INFINITY;
        // Sell day
        for (int i = 0; i < prices.length; i++) {
            double buyPrice = prices[i];
            for (int j=i; j < prices.length; j++) {
                double sellPrice = prices[j];
                double currentRev = sellPrice - buyPrice;
                if (currentMaxRev < currentRev) {
                    currentMaxRev = currentRev;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }
        return result;
    }


}
