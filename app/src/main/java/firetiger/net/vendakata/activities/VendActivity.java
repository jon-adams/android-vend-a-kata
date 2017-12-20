package firetiger.net.vendakata.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import firetiger.net.vendakata.R;
import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.Stock;
import firetiger.net.vendakata.models.VendingMachine;
import firetiger.net.vendakata.services.IVendService;

public class VendActivity extends AppCompatActivity {
    private TextView viewTextDisplay;

    private IVendService vendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vend);

        viewTextDisplay = findViewById(R.id.vend_display);

        // setup naïve vending service that gets reset during any activity lifecycle reset
        List<Stock> stock = new ArrayList<>();
        stock.add(new Stock(new Product(getString(R.string.product_1), 100), 2));
        stock.add(new Stock(new Product(getString(R.string.product_2), 50), 1));
        stock.add(new Stock(new Product(getString(R.string.product_3), 65), 50));
        vendService = new VendingMachine(stock);
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewTextDisplay.setText(vendService.updateAndGetCurrentMessageForDisplay());
    }
}
