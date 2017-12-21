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

public class VendingServiceSoldOutFeature {
    private IVendService machine;

    private final Product cola = new Product("cola", 100);

    @Before
    public void setUp() {
        List<Stock> stock = new ArrayList<>(3);
        stock.add(new Stock(cola, 0));
        stock.add(new Stock(new Product("chips", 50), 0));
        stock.add(new Stock(new Product("candy", 65), 0));

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
    public void soldOutWithNoAccepted() {
        assertFalse(this.machine.purchaseProduct(0));
        assertEquals(0, this.machine.getAcceptedUsc());
        assertEquals("SOLD OUT", this.machine.updateAndGetCurrentMessageForDisplay());
        assertEquals("INSERT COIN", this.machine.updateAndGetCurrentMessageForDisplay());
    }

    @Test
    public void soldOutWithAccepted() {
        this.machine.insertCoin(5);
        assertFalse(this.machine.purchaseProduct(0));
        assertEquals(5, this.machine.getAcceptedUsc());
        assertEquals("SOLD OUT", this.machine.updateAndGetCurrentMessageForDisplay());
        assertEquals("$0.05", this.machine.updateAndGetCurrentMessageForDisplay());
    }
}