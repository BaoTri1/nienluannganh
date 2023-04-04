package com.example.shopproject.orther_handle;

import com.example.shopproject.mode.Product;

import java.util.Comparator;

public class PriceComparaterLowHigh implements Comparator<Product> {
    @Override
    public int compare(Product product, Product t1) {
        if(product.getPrice() == t1.getPrice())
            return 0;
        else if(product.getPrice() > t1.getPrice())
            return 1;
        else
            return -1;
    }
}
