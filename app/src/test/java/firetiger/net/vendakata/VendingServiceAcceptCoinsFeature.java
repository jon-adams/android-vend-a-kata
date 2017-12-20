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

public class VendingServiceAcceptCoinsFeature {
    private IVendService machine;

    @Before
    public void setUp() {
        List<Stock> stock = new ArrayList<>(3);
        stock.add(new Stock(new Product("grapes", 0), 2));
        stock.add(new Stock(new Product("wrath", 50), 2));
        stock.add(new Stock(new Product("ivory", 300), 2));

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
    public void acceptValidNickel() {
        this.machine.insertCoin(5);
        assertEquals(0, this.machine.getUscInReturn());
        assertEquals("$0.05", this.machine.updateAndGetCurrentMessageForDisplay());
    }

    @Test
    public void acceptValidDime() {
        this.machine.insertCoin(10);
        assertEquals(0, this.machine.getUscInReturn());
        assertEquals("$0.10", this.machine.updateAndGetCurrentMessageForDisplay());
    }

    @Test
    public void acceptValidQuarter() {
        this.machine.insertCoin(25);
        assertEquals(0, this.machine.getUscInReturn());
        assertEquals("$0.25", this.machine.updateAndGetCurrentMessageForDisplay());
    }

    @Test
    public void acceptValidCoins() {
        this.machine.insertCoin(5);
        this.machine.insertCoin(10);
        this.machine.insertCoin(25);
        this.machine.insertCoin(25);
        assertEquals(0, this.machine.getUscInReturn());
        assertEquals("$0.65", this.machine.updateAndGetCurrentMessageForDisplay());
    }

    @Test
    public void rejectInvalidCoins() {
        assertEquals("INSERT COIN", this.machine.updateAndGetCurrentMessageForDisplay());
        this.machine.insertCoin(1);
        assertEquals(1, this.machine.getUscInReturn());
        assertEquals("INSERT COIN", this.machine.updateAndGetCurrentMessageForDisplay());
        this.machine.insertCoin(2);
        assertEquals(3, this.machine.getUscInReturn());
    }
}