package com.example.shopproject.view.UI.Fragment.callback;

import com.example.shopproject.mode.ShippingMethod;

public interface CallbackFragment {
     default void UpdateBadge(int number){};
     default void getListProductFilter(String catalog, String price, String rate){};
     default void passShippingMethod(ShippingMethod shippingMethod){};
     default void passPaymentMethod(String nameMethod){};
}
