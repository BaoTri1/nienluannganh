package com.example.shopproject.presenter.callbackMode;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Orders;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.mode.orderResponse;
import com.example.shopproject.mode.reviewResponse;

import java.util.List;

public interface CallbackProductMode {
    default void getListProductSuccess(List<Product> mlist){};
    default void getListFavoriteProductSuccess(List<Product> mlist){};
    default void ListFavoriteProductisEmpty(){};
    default void getDataFailure(String message){};
    default void getListCatalogsSuccess(List<String> listCatalogs){};
    default void getProductSuccess(Product product){};
    default void getImageProductSuccess(List<Photo> listimg){};
    default void getNumProductAvailable(int number){};
    default void getSearchResponseSuccess(SearchResponse searchResponse){};
    default void getListCartSuccess(List<Items> listItems){};
    default void ordersSuccess(orderResponse orderResponse){};
    default void reviewSuccess(reviewResponse reviewResponse){};
    default void getListOrdersHistorySuccess(List<Orders> mList){};
}
