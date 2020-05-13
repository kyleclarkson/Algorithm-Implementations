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

    /**
     * A Divide-and-conquer based solution. Based on the following observation:
     *      Define a window (x,y) as in interval of some length. Given an interval of length
     *      k, partition this into two intervals each of length k/2. Observe the structure of
     *      the optimal window O=(x',y'):
     *          either O lies in the first or last interval,
     *          or O spans between the both, with the buy day in the former and the
     *              sell day in the latter.
     * Analysis of the algorithm (Master's Theorem) yields a O(nlogn) runtime.
     * @return An int 1x2 array. First and second values indicating indices to buy and sell.
     */
    public int[] DNC(int leftIndex, int rightIndex) {
        int[] result = new int[2];
        // TODO right out using window size as parameter.

        // Base case, window size is one
        if (rightIndex - leftIndex <= 1) {
            return new int[] {leftIndex, rightIndex};
        }
        // Inductive case
        else {
            int midpoint = (rightIndex + leftIndex) / 2;
//            System.out.println("midpoint: "+midpoint);
            // Search for optimal solution in left, right windows.
            int[] leftSol = DNC(leftIndex, midpoint);
            int[] rightSol = DNC(midpoint + 1, rightIndex);

            // Search for optimal window across midpoint.
            int indexBuy = getMinIndex(prices, leftIndex, midpoint);
            int indexSell = getMaxIndex(prices, midpoint+1, rightIndex);

            // Return indices correspond to max of three cases:
            double[] maxValues = new double[] {
                    prices[leftSol[1]] - prices[leftSol[0]],
                    prices[rightSol[1]] - prices[rightSol[0]],
                    prices[indexBuy] - prices[indexSell]
            };

            switch (getMaxIndex(maxValues,0, 2)) {
                case 0:
                    return leftSol;
                case 1:
                    return rightSol;
                case 2:
                    return new int[] {indexBuy, indexSell};
            }
        }


        return result;
    }

    /**
     * Get index of min value in array.
     * @param array         - Array to search over
     * @param startIndex    - Starting index (inclusive)
     * @param endIndex      - Ending index (exclusive)
     * @return              - Index of min value in array.
     */
    private int getMinIndex(double[] array, int startIndex, int endIndex) {
        double minValue = Double.POSITIVE_INFINITY;
        int index = startIndex;
        for (int i=startIndex; i<endIndex; i++) {
            if (array[i] < minValue) {
                index = i;
                minValue = array[i];
            }
        }
        return index;
    }
    /**
     * Get index of max value in array.
     * @param array         - Array to search over
     * @param startIndex    - Starting index (inclusive)
     * @param endIndex      - Ending index (exclusive)
     * @return              - Index of max value in array.
     */
    private int getMaxIndex(double[] array, int startIndex, int endIndex) {
        double maxValue = Double.NEGATIVE_INFINITY;
        int index = startIndex;
        for (int i=startIndex; i<endIndex; i++) {
            if (array[i] > maxValue) {
                index = i;
                maxValue = array[i];
            }
        }
        return index;
    }
}
