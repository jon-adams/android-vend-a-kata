package firetiger.net.vendakata.models;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import firetiger.net.vendakata.services.IVendService;

/**
 * A vending machine
 */
public class VendingMachine implements IVendService {
    /*
     * NOTE: Coins are counted internally as integers, and only converted to
     * decimal USD on output, to simplify accuracy and increase performance
     */

    /**
     * Nickel ($0.05)
     */
    private final int COIN_NICKEL = 5;

    /**
     * Dime ($0.10)
     */
    private final int COIN_DIME = 10;

    /**
     * Quarter ($0.25)
     */
    private final int COIN_QUARTER = 25;

    private final List<Stock> availableStock;

    /**
     * In-flight/current value of currency provided by the current/last user
     * for use in purchases
     * <p>
     * Starts with no currency in flight—no freebies for the first user!
     */
    private int currencyInUsc = 0;

    private int returnInUsc = 0;

    /**
     * Track how much change is available in the machine.
     * <p>
     * For this demo, assumes the value can be covered by any amount of coins.
     * In other words, does not track individual coins available as change.
     * <p>
     * Starts with $4.00 in change (since the kata requirements did not specify
     * the amount)
     */
    private int changeInUsc = 4;

    /**
     * Construct a machine instance
     *
     * @param availableStock available products and their current stock
     */
    public VendingMachine(@NonNull List<Stock> availableStock) {
        this.availableStock = availableStock;
    }

    @Override
    public String toString() {
        return String.format(Locale.US,
                "$%3.2f in flight, $%3.2f in change, and %d products",
                (float) currencyInUsc / 100,
                (float) changeInUsc / 100,
                availableStock.size());
    }

    @Override
    public boolean insertCoin(int usc) {
        // TODO: covert USD to internal integer
        // TODO: implement insertCoin()
        return false;
    }

    @NonNull
    @Override
    public String updateAndGetCurrentMessageForDisplay() {
        return null;
    }

    @Override
    public int getAcceptedUsc() {
        return currencyInUsc;
    }

    @Override
    public int getUscInReturn() {
        return returnInUsc;
    }

    @Override
    public boolean purchaseProduct(@NonNull Product product) {
        return false;
    }

    @Override
    public void returnCoins() {
        // TODO: implement returnCoins()
    }

    @Override
    public void collectCoins() {
        this.returnInUsc = 0;
    }
}
