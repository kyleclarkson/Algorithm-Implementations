package BuySellDays;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTest {

    double[] prices1 = {2,5,8,7,15,14,30,20,15,2,3,7};
    int[] solution1 = {0, 6};

    @Test
    public void Naive1() {
        BuySellDays buySellDays = new BuySellDays(prices1);
        assertArrayEquals(solution1, buySellDays.Naive());
    }

    @Test
    public void Naive2() {
        BuySellDays buySellDays = new BuySellDays(prices1);
        assertArrayEquals(solution1, buySellDays.Naive());
    }

    @Test
    public void DNC1() {
        BuySellDays buySellDays = new BuySellDays(prices1);
        assertArrayEquals(solution1, buySellDays.DNC(0, prices1.length));
    }
}
