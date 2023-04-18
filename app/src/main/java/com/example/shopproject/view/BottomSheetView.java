package com.example.shopproject.view;

import com.example.shopproject.mode.Color;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Product;

import java.util.List;

public interface BottomSheetView {
    void DisplayProduct(Product product);
    void DisplayColorsAndSize(List<Color> colors);
    void DisplayQuantity(String quantity);
    void DisplayQuantityError(String message);
    void DisplayImgageDialog(String image);
    void PassItemCart(Items items);
    void PassItemOrder(List<Items> items);
    void DisplayError(String message);
}
