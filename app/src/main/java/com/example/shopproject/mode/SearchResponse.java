package com.example.shopproject.mode;

import java.util.List;

public class SearchResponse {

    private List<Product> products;
    private int countProducts;
    private int page;
    private int pages;

    public SearchResponse(List<Product> products, int countProducts, int page, int pages) {
        this.products = products;
        this.countProducts = countProducts;
        this.page = page;
        this.pages = pages;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getCountProducts() {
        return countProducts;
    }

    public void setCountProducts(int countProducts) {
        this.countProducts = countProducts;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
