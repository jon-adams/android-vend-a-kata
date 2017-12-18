package firetiger.net.vendakata;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.VendingMachine;
import firetiger.net.vendakata.services.IVendService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class VendingServiceAcceptCoinsFeature {
    private IVendService machine;

    @Before
    public void setUp() {
        List<Product> products = new ArrayList<Product>(3);
        products.add(new Product("grapes", 0.0f));
        products.add(new Product("wrath", 0.50f));
        products.add(new Product("ivory", 5.0f));

        // cast will fail for now—TDD; will implement next
        this.machine = (IVendService) new VendingMachine(products);
    }

    @Test
    public void validMachineCreatedDuringTestSetup() throws Exception {
        assertNotNull("Machine should have been setup by the fixture", this.machine);
    }

    @Test
    public void checkTestDataSetup() throws Exception {
        assertEquals("Expected several products to be created for the standard machine setup", 0f, machine.getCoinsInReturn(), 0.01f);
    }
}