package com.example.shopproject.repository;

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
}
