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
import static org.junit.Assert.assertNotNull;

public class VendingServiceExactChangeFeature {
    private IVendService machine;

    private Product cola = new Product("cola", 5);

    @Before
    public void setUp() {
        List<Stock> stock = new ArrayList<>(3);
        stock.add(new Stock(cola, 200));
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
    public void exactChange() {
        // machine starts with a default of 400 ($4.00);
        // loop through until it is empty (based on cost minus change but
        // plus the cost... so evens out to whatever was entered to start with)
        for (int i = 0; i < Math.ceil(400f / 25f); i++) {
            this.machine.insertCoin(25);
            this.machine.purchaseProduct(cola);
        }

        assertEquals("EXACT CHANGE ONLY", this.machine.updateAndGetCurrentMessageForDisplay());
    }
}