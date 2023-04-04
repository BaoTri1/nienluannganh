package com.example.shopproject.orther_handle;

import com.example.shopproject.mode.Product;

import java.util.Comparator;

public class PriceComparaterHighLow implements Comparator<Product> {
    @Override
    public int compare(Product product, Product product1) {
        if(product.getPrice() == product1.getPrice())
            return 0;
        else if(product.getPrice() < product1.getPrice())
            return 1;
        else
            return -1;
    }
}
