package com.example.shopproject.repository;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.ShippingAddress;

import java.util.List;

public interface ProductRepository {
    void getListProducts();
    void getListCatalogs();
    void getProduct(String slug);
    void getListFavoriteProducts();
    void getCountSize(String slug, int indexColor, int indexSize);
    void getCountColor(String slug, int indexColor);
    void getCountInStock(String slug);
    void SearchProductsByCategory(String category);
    void SearchProductsByQuery(String query);
    void getListProductFilter(String category, String query, String price, String rating, String order);
    void getListCart();
    void PostOrders(List<Items> items, ShippingAddress shippingAddress, String paymentMethod, int toltalPriceProcuct, int shippingPrice, int taxPrice, int toltalPayment);
}
