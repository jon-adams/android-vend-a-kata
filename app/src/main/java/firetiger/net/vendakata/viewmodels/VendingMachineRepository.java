package firetiger.net.vendakata.viewmodels;

import java.util.ArrayList;
import java.util.List;

import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.Stock;
import firetiger.net.vendakata.models.VendingMachine;
import firetiger.net.vendakata.services.IVendService;

/**
 * Singleton repository that creates vending machines.
 * <p>
 * A bit overkill for this app, but showcases the pattern for use cases like network access or
 * other IO or slow operations.
 */
public final class VendingMachineRepository {
    // normally would inject this as a singleton as an IoC; instead, old-school for the demo
    private static VendingMachineRepository instance = null;

    // and each repository only needs one VendingMachine
    private static IVendService vendingMachine = null;

    private VendingMachineRepository() {
        // normally stock would load out of a database, but hard-coded for demo
        final List<Stock> stock = new ArrayList<>();
        stock.add(new Stock(new Product("Cola", 100), 2));
        stock.add(new Stock(new Product("Chips", 50), 1));
        stock.add(new Stock(new Product("Candy", 65), 50));
        vendingMachine = new VendingMachine(stock);
    }

    public static VendingMachineRepository getInstance() {
        if (instance == null) {
            instance = new VendingMachineRepository();
        }

        return instance;
    }

    IVendService getVendingMachine() {
        return vendingMachine;
    }
}
