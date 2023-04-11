package com.example.shopproject.view.UI.Fragment.callback;

import com.example.shopproject.mode.Items;

public interface CallbackFragment {
     default void AddCartSuccess(Items items){};
     default void getListProductFilter(String catalog, String price, String rate){};

}
