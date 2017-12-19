package firetiger.net.vendakata;

import org.junit.Test;

import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.Stock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StockSmokeTest {
    @Test
    public void validStock() throws Exception {
        Stock s = new Stock(new Product("Something", 5), 2);
        assertNotNull("Stock should not have been null", s);
        assertNotNull(s.getProduct());
        assertEquals(2, s.getAvailable());
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCost() throws Exception {
        new Stock(new Product("Negative costs ain't cool, yo", 0), -2);
    }

    @Test
    public void testToString() throws Exception {
        assertEquals(
                "toString result did not match expected",
                "x5 a name/$0.04",
                new Stock(new Product("a name", 4), 5).toString());
    }
}