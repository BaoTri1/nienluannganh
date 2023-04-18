package com.example.shopproject.view.UI.Fragment.callback;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.ShippingMethod;

public interface CallbackFragment {
     default void AddCartSuccess(Items items){};
     default void getListProductFilter(String catalog, String price, String rate){};
     default void passShippingMethod(ShippingMethod shippingMethod){};
     default void passPaymentMethod(String nameMethod){};

}
