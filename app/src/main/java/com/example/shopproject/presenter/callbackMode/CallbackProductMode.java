package com.example.shopproject.presenter.callbackMode;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.SearchResponse;

import java.util.List;

public interface CallbackProductMode {
    default void getListProductSuccess(List<Product> mlist){};
    default void getListFavoriteProductSuccess(List<Product> mlist){};
    default void ListFavoriteProductisEmpty(){};
    default void getDataFailure(String message){};
    default void getListCatalogsSuccess(List<String> listCatalogs){};
    default void getProductSuccess(Product product){};
    default void getNumProductAvailable(int number){};
    default void getSearchResponseSuccess(SearchResponse searchResponse){};
    default void getListCartSuccess(List<Items> listItems){};
}
