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

    private Product cola = new Product("ROYAL crown cola", 500);

    @Before
    public void setUp() {
        List<Stock> stock = new ArrayList<>(1);
        stock.add(new Stock(cola, 200));
        this.machine = new VendingMachine(stock);
    }

    @Test
    public void validMachineCreatedDuringTestSetup() throws Exception {
        assertNotNull(
                "Machine should have been setup by the fixture",
                this.machine);
        assertEquals(
                "Expected no money pending return in the standard machine setup",
                0,
                machine.getUscInReturn());
    }

    @Test
    public void exactChange() {
        // expects the machine to be setup with any product worth more than the default starting change of $4
        assertEquals("EXACT CHANGE ONLY", this.machine.updateAndGetCurrentMessageForDisplay());
    }
}