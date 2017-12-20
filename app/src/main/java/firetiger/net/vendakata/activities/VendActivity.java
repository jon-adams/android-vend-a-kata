package firetiger.net.vendakata.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import firetiger.net.vendakata.R;
import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.Stock;
import firetiger.net.vendakata.models.VendingMachine;
import firetiger.net.vendakata.services.IVendService;

public class VendActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView viewTextDisplay;

    private Button viewButtonChange;

    private IVendService vendService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_vend);

        // setup naïve vending service that gets reset during any activity lifecycle reset
        List<Stock> stock = new ArrayList<>();
        stock.add(new Stock(new Product(getString(R.string.product_1), 100), 2));
        stock.add(new Stock(new Product(getString(R.string.product_2), 50), 1));
        stock.add(new Stock(new Product(getString(R.string.product_3), 65), 50));
        this.vendService = new VendingMachine(stock);

        // view binding and programmatic access
        TextView product1 = findViewById(R.id.vend_btn_purchase_1);
        TextView product2 = findViewById(R.id.vend_btn_purchase_2);
        TextView product3 = findViewById(R.id.vend_btn_purchase_3);
        product1.setText(stock.get(0).getProduct().getName());
        product2.setText(stock.get(1).getProduct().getName());
        product3.setText(stock.get(2).getProduct().getName());

        // handlers
        findViewById(R.id.vend_btn_return).setOnClickListener(this);
        findViewById(R.id.vend_btn_insert).setOnClickListener(this);
        product1.setOnClickListener(this);
        product2.setOnClickListener(this);
        product3.setOnClickListener(this);

        this.viewButtonChange = findViewById(R.id.vend_btn_collect);
        this.viewButtonChange.setOnClickListener(this);

        this.viewTextDisplay = findViewById(R.id.vend_display);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.viewTextDisplay.setText(vendService.updateAndGetCurrentMessageForDisplay());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.vend_btn_collect:
                this.vendService.collectCoins();
                break;
            case R.id.vend_btn_insert:
                // TODO: show input dialog for value of coin
                this.vendService.insertCoin(25);
                break;
            case R.id.vend_btn_purchase_1:
                this.vendService.purchaseProduct(0);
                break;
            case R.id.vend_btn_purchase_2:
                this.vendService.purchaseProduct(1);
                break;
            case R.id.vend_btn_purchase_3:
                this.vendService.purchaseProduct(2);
                break;
            case R.id.vend_btn_return:
                this.vendService.returnCoins();
                break;
        }

        // now that action performed, update the display accordingly
        this.viewTextDisplay.setText(this.vendService.updateAndGetCurrentMessageForDisplay());

        // and show any change for collection
        this.viewButtonChange.setText(
                this.getString(
                        R.string.vend_action_collect,
                        (float) this.vendService.getUscInReturn() / 100));
    }
}
