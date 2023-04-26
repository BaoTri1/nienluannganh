package com.example.shopproject.view;

import com.example.shopproject.mode.Product;

import java.util.List;

public interface ProductView {
    //void DisplayListProduct(List<Product> listProduct);
    void DisplayListProduct(List<Product> listProduct, List<String> listID);
    void DisplayNumProducts(String message);
    void DisplayNoNetWork(String message);
    void DisplayBadge(int number);
    void DisplayError(String message);
}
