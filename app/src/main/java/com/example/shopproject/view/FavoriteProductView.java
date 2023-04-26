package com.example.shopproject.view;

import com.example.shopproject.mode.Product;

import java.util.List;

public interface FavoriteProductView {
    void DisplayListFavoriteProduct(List<Product> mList);
    void DisplaySortList(List<Product> mList);
    void DisplayChangeList(List<Product> mList);
    void DisplayisListEmpty();
    void DisplayNumIcon(int number);
    void DisplayNoNetWork(String message);
    void DisplayError(String message);
}
