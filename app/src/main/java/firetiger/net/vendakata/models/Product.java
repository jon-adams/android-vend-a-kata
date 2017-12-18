package firetiger.net.vendakata.models;

import android.support.annotation.NonNull;

import java.util.Locale;

/**
 * A product
 */
public class Product {
    private final String name;

    private final float costInUsd;

    /**
     * Construct a product instance
     *
     * @param name      product name; must not be empty
     * @param costInUsd cost in USD (US dollars)
     */
    public Product(@NonNull String name, float costInUsd) {
        if ("".equals(name)) {
            throw new IllegalArgumentException("name may not be empty");
        }

        if (costInUsd < 0) {
            throw new IllegalArgumentException("costInUsd must be zero or greater");
        }

        this.name = name;
        this.costInUsd = costInUsd;
    }

    public String getName() {
        return name;
    }

    public float getCostInUsd() {
        return costInUsd;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "%s/$%3.2f", name, costInUsd);
    }
}
