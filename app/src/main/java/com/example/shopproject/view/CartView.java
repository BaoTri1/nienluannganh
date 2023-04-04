package com.example.shopproject.view;

import com.example.shopproject.mode.Items;

import java.util.List;

public interface CartView {
    void DisplayListCartSuccess(List<Items> listItems);
    void DisplayListCartUdated(List<Items> list);
    void DisplaytotlalPrice(int total);
    void DisplayNumProduct(int num);
    void DisplayQuantityError(String message);
    void DisplayNoNetWork(String message);
    void DisplayError(String message);
}
