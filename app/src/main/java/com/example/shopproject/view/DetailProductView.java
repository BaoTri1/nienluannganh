package com.example.shopproject.view;

import com.example.shopproject.mode.Color;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.Review;
import com.example.shopproject.mode.Size;

import java.util.List;

public interface DetailProductView {
    void DisplayInforProduct(Product product);
    void DisplayImagesProduct(List<Photo> list);
    void DisplayReviews(float rate, int numRviews, List<Review> reviews);
    void DisplayListProduct(List<Product> mList);
    void DisplayBadge(int number);
    void DisplayNoNetWork(String message);
    void DisplayError(String message);

}
