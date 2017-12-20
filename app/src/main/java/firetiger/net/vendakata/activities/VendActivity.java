package firetiger.net.vendakata.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import firetiger.net.vendakata.R;
import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.models.Stock;
import firetiger.net.vendakata.models.VendingMachine;
import firetiger.net.vendakata.services.IVendService;

public class VendActivity extends AppCompatActivity implements View.OnClickListener, AlertDialog.OnClickListener {
    private TextView viewTextDisplay;

    private Button viewButtonChange;

    private AlertDialog dlgInsertCoins;

    private EditText dlgInsertCoinsInput;

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

        // build the "Insert Coins" dialog;
        // kata requirements did not specify how and/or how easy it should be to input so, for simplicity, using a text input dialog
        this.dlgInsertCoinsInput = new EditText(this);
        this.dlgInsertCoinsInput.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        this.dlgInsertCoins = new AlertDialog.Builder(this)
                .setTitle(R.string.vend_dlg_insert_coin)
                .setMessage(R.string.vend_help_coin)
                .setView(this.dlgInsertCoinsInput)
                .setPositiveButton(android.R.string.ok, this)
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull final DialogInterface dialog, final int which) {
                        dialog.cancel();
                    }
                }).create();
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
                this.dlgInsertCoins.show();
                // since display only needs updated after successful insert,
                // exit now since updateDisplay() is called in the click handler callback
                return;
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

        updateDisplay();
    }

    private void updateDisplay() {
        // now that action performed, update the display accordingly
        this.viewTextDisplay.setText(this.vendService.updateAndGetCurrentMessageForDisplay());

        // and show any change for collection
        this.viewButtonChange.setText(
                this.getString(
                        R.string.vend_action_collect,
                        (float) this.vendService.getUscInReturn() / 100));
    }

    @Override
    public void onClick(@NonNull final DialogInterface dialog, final int which) {
        try {
            float usdValue = Float.parseFloat(this.dlgInsertCoinsInput.getText().toString());
            int coinValue = (int) Math.floor(usdValue * 100);

            if (VendActivity.this.vendService.insertCoin(coinValue)) {
                updateDisplay();
                // since dialog can be reused, reset text when successful Insert was allowed
                this.dlgInsertCoinsInput.setText("");
            } else {
                // the coin was not accepted, but was put in the change tray immediately, so
                // update the display...
                updateDisplay();

                // ...then tell the user it was not accepted
                Toast.makeText(
                        VendActivity.this,
                        R.string.vend_dlg_invalid,
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } catch (NumberFormatException exc) {
            Toast.makeText(
                    VendActivity.this,
                    R.string.vend_dlg_invalid,
                    Toast.LENGTH_SHORT)
                    .show();
        }

        dialog.dismiss();
    }
}
