package firetiger.net.vendakata.services;

import android.support.annotation.NonNull;

import firetiger.net.vendakata.models.Product;

/**
 * Service for core vend functionality
 */
public interface IVendService {
    /**
     * Accepts a coin (based on its value) and adds to current in-process cash if valid
     *
     * @param usd US dollar amount of the coin inserted
     * @return true if the coin was accepted; false if the coin was rejected and added to the coins-in-return
     */
    public boolean insertCoin(float usd);

    /**
     * Updates the current display message based on the state of the vending machine and returns what that should show to the user
     * <p>
     * Non-deterministic: display may change from one call to the next depending on the state of the machine
     *
     * @return the message to display to the user
     */
    @NonNull
    public String updateAndGetCurrentMessageForDisplay();

    /**
     * Gets the value of coins in the return (either that were rejected during insert, or are in-process and user requested a return
     *
     * @return the value of coins in the return
     */
    public float getCoinsInReturn();

    /**
     * Purchases the requested product.
     * <p>
     * If enough currency and product is available, will deliver to the user and deduct the value from the in-process cash.
     * <p>
     * If the product may not be purchased at this time, will return false.
     * <p>
     * Either success or failure will update the display appropriately; make sure to check and display to the user
     *
     * @param product the requested product
     * @return true if the product was purchased and delivered to the user; false if invalid for any reason
     */
    public boolean purchaseProduct(@NonNull Product product);
}
