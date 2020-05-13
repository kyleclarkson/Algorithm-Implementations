package BuySellDays;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest {

    @Test
    public void testBuySellDays1() {
        double[] prices = {2,5,8,7,15,14,30,20,15,2,3,7};
        int[] solution = {0, 6};
        BuySellDays buySellDays = new BuySellDays(prices);
        assertArrayEquals(solution, buySellDays.Naive());
    }

    @Test
    public void testBuySellDays2() {
        double[] prices = {20,19,18,15,13,11,7,5,4,2,-1};
        int[] solution = {0, 0};
        BuySellDays buySellDays = new BuySellDays(prices);
        assertArrayEquals(solution, buySellDays.Naive());

    }
}
