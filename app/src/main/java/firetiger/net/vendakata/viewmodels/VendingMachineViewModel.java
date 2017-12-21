package firetiger.net.vendakata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import firetiger.net.vendakata.R;
import firetiger.net.vendakata.models.Product;
import firetiger.net.vendakata.services.IVendService;

public final class VendingMachineViewModel extends AndroidViewModel {
    @NonNull
    private final MutableLiveData<String> display = new MutableLiveData<String>();

    @NonNull
    private final MutableLiveData<String> change = new MutableLiveData<String>();

    @NonNull
    private final MutableLiveData<String> product1 = new MutableLiveData<String>();

    @NonNull
    private final MutableLiveData<String> product2 = new MutableLiveData<String>();

    @NonNull
    private final MutableLiveData<String> product3 = new MutableLiveData<String>();

    @Nullable
    private IVendService vendingMachine;

    public VendingMachineViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(@NonNull VendingMachineRepository vendingMachineRepository) {
        // only create a new one if necessary
        if (this.vendingMachine == null) {
            this.vendingMachine = vendingMachineRepository.getVendingMachine();

            // initialize live data
            List<Product> products = this.vendingMachine.getProducts();
            product1.postValue(products.get(0).getName());
            product2.postValue(products.get(1).getName());
            product3.postValue(products.get(2).getName());

            updateDisplay();
        }
    }

    public LiveData<String> getVendingMachineDisplay() {
        return this.display;
    }

    public LiveData<String> getVendingMachineChangeDisplay() {
        return this.change;
    }

    public LiveData<String> getVendingMachineProductDisplay(int productIndex) {
        switch (productIndex) {
            case 0:
                return this.product1;
            case 1:
                return this.product2;
            case 2:
                return this.product3;
            default:
                throw new IllegalArgumentException(
                        "Only 3 products available but item " + (productIndex - 1) + " was requested");
        }
    }

    public void collectCoins() {
        if (this.vendingMachine == null) {
            throw new UnsupportedOperationException("you must call init() before calling any other methods in this view model");
        }

        this.vendingMachine.collectCoins();
        updateDisplay();
    }

    public boolean insertCoin(int coinValue) {
        if (this.vendingMachine == null) {
            throw new UnsupportedOperationException("you must call init() before calling any other methods in this view model");
        }

        final boolean result = this.vendingMachine.insertCoin(coinValue);
        updateDisplay();
        return result;
    }

    public boolean purchaseProduct(int productIndex) {
        if (this.vendingMachine == null) {
            throw new UnsupportedOperationException("you must call init() before calling any other methods in this view model");
        }

        final boolean result = this.vendingMachine.purchaseProduct(productIndex);
        updateDisplay();
        return result;
    }

    public void returnCoins() {
        if (this.vendingMachine == null) {
            throw new UnsupportedOperationException("you must call init() before calling any other methods in this view model");
        }

        this.vendingMachine.returnCoins();
        updateDisplay();
    }

    private void updateDisplay() {
        if (this.vendingMachine == null) {
            throw new UnsupportedOperationException("you must call init() before calling any other methods in this view model");
        }

        this.display.postValue(this.vendingMachine.updateAndGetCurrentMessageForDisplay());
        this.change.postValue(
                this.getApplication().getResources().getString(R.string.vend_action_collect,
                (float) this.vendingMachine.getUscInReturn() / 100));
    }
}
