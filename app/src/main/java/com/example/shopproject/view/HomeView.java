package com.example.shopproject.view;

import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;

import java.util.List;

public interface HomeView {
    void DisplayBanner(List<Photo> listPhoto);
    void DisplayCatalog(List<String> listCatalog);
    void DisplayListProduct(List<Product> listProduct, List<String> listId);
    void DisplayNumIcon(int number);
    void DisplayNoNetWork(String message);
    void DisplayError(String message);
}
