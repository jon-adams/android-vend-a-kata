package firetiger.net.vendakata;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.Stock;
import firetiger.net.vendakata.models.VendingMachine;
import firetiger.net.vendakata.services.IVendService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class VendingServiceSelectProductFeature {
    private IVendService machine;

    private Product cola = new Product("cola", 100);

    @Before
    public void setUp() {
        List<Stock> stock = new ArrayList<>(3);
        stock.add(new Stock(cola, 2));
        stock.add(new Stock(new Product("chips", 50), 2));
        stock.add(new Stock(new Product("candy", 65), 2));

        this.machine = new VendingMachine(stock);
    }

    @Test
    public void validMachineCreatedDuringTestSetup() throws Exception {
        assertNotNull(
                "Machine should have been setup by the fixture",
                this.machine);
    }

    @Test
    public void checkTestDataSetup() throws Exception {
        assertEquals(
                "Expected no money pending return in the standard machine setup",
                0,
                machine.getUscInReturn());
    }

    @Test
    public void purchaseProductHappyPath() {
        this.machine.insertCoin(5);
        this.machine.insertCoin(25);
        this.machine.insertCoin(25);
        this.machine.insertCoin(25);
        this.machine.insertCoin(25);
        assertEquals(105, this.machine.getAcceptedUsc());
        assertTrue(this.machine.purchaseProduct(cola));
        assertEquals(0, this.machine.getAcceptedUsc());
        assertEquals("THANK YOU", this.machine.updateAndGetCurrentMessageForDisplay());
        assertEquals("INSERT COIN", this.machine.updateAndGetCurrentMessageForDisplay());
    }

    @Test
    public void purchaseProductInsufficientFunds() {
        assertFalse(this.machine.purchaseProduct(cola));
        assertEquals("INSERT COIN", this.machine.updateAndGetCurrentMessageForDisplay());
        this.machine.insertCoin(5);
        assertFalse(this.machine.purchaseProduct(cola));
        assertEquals("$0.05", this.machine.updateAndGetCurrentMessageForDisplay());
    }
}