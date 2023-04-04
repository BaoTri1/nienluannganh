package com.example.shopproject.view.adapter.interfaceListenerAdapter;

import com.example.shopproject.mode.Color;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.Size;

public interface clickListener {
    default void itemCatalogClick(String category){};
    default void onClickDetailProduct(Product product){};

    default void onClickLike(Product product){};
    default void onClickAddCart(Product product){};
    default void onClickBuyNow(Product product){};
    default void onClickDelete(Product product){};
    default void itemColorClick(int index, Color color){};
    default void itemSizeClick(int index, Size size){};

    default void onClickHistorySearch(String search){};

    default void deleteItem(Items items){};
    default void addYeuThich(Items items){};
    default  void decrement(Items items){};
    default void increment(Items items){};
}
