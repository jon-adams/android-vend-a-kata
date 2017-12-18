package firetiger.net.vendakata;

import org.junit.Test;

import firetiger.net.vendakata.models.Product;

import static org.junit.Assert.assertNotNull;

public class ProductSmokeTest {
    @Test
    public void validProduct() throws Exception {
        Product p = new Product("Something", 0.5f);
        assertNotNull("Product should not have been null", p);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidProductName() throws Exception {
        new Product("", 0.5f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidCost() throws Exception {
        new Product("Negative costs ain't cool, yo", -0.5f);
    }
}