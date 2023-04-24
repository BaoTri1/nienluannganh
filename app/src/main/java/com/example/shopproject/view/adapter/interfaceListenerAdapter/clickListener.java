package com.example.shopproject.view.adapter.interfaceListenerAdapter;

import com.example.shopproject.mode.Color;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Orders;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.ShippingMethod;
import com.example.shopproject.mode.Size;
import com.example.shopproject.sqlite.Entity.Address;

public interface clickListener {
    default void itemCatalogClick(String category){};
    default void onClickDetailProduct(Product product){};

    default void onClickLike(Product product){};
    default void onClickDelete(Product product){};
    default void itemColorClick(int index, Color color){};
    default void itemSizeClick(int index, Size size){};

    default void onClickHistorySearch(String search){};

    default void deleteItem(Items items){};
    default void addYeuThich(Items items){};
    default  void decrement(Items items){};
    default void increment(Items items){};

    default void onClickShippingMethod(ShippingMethod shippingMethod){};

    default void onClickDetailOrders(Orders orders){};

    default void onClickAddress(Address address){};
}
