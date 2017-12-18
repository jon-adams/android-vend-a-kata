package firetiger.net.vendakata.models;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

/**
 * A vending machine
 */
public class VendingMachine /* : IVendService */ {
    private final List<Product> availableProducts;
    /**
     * In-flight/current value of currency provided by the current/last user
     * for use in purchases
     * <p>
     * Starts with no currency in flight—no freebies for the first user!
     */
    private float currencyInUsd = 0f;
    /**
     * Track how much change is available in the machine.
     * <p>
     * For this demo, assumes the value can be covered by any amount of coins.
     * In other words, does not track individual coins available as change.
     * <p>
     * Starts with $4.00 in change (since the kata requirements did not specify
     * the amount)
     */
    private float changeInUsd = 4.0f;

    /**
     * Construct a machine instance
     *
     * @param availableProducts available products
     */
    public VendingMachine(@NonNull List<Product> availableProducts) {
        this.availableProducts = availableProducts;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "$%3.2f in flight, $%3.2f in change, and %d products",
                currencyInUsd,
                changeInUsd,
                availableProducts.size());
    }
}
