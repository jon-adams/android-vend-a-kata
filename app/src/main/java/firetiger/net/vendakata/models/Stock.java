package firetiger.net.vendakata.models;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * The stock of a product for use in a {@link VendingMachine}
 */
public class Stock {
    private final int available;

    @NonNull
    private final Product product;

    public Stock(@NonNull Product product, int available) {
        if (available < 0) {
            throw new IllegalArgumentException("stock must be zero or greater");
        }

        this.product = product;
        this.available = available;
    }

    public int getAvailable() {
        return available;
    }

    @NonNull
    public Product getProduct() {
        return product;
    }

    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "x%d %s",
                available,
                product.toString());
    }
}
