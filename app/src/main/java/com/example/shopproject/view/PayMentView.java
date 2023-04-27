package com.example.shopproject.view;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.orderResponse;

import java.util.List;

public interface PayMentView {
    void DisplayShippingAddress(String fullName, String sdt, String address);
    void DisplayListItemOrder(List<Items> items);
    void DisplayToltalPriceProduct(String toltalPriceProduct);
    void DisplayToltalPayment(String toltalPricePayment);
    void DisplayToltalDiscount(String toltalPriceDiscount);
    void OpenDetailOrder(String namePaymentMethod, orderResponse orderResponse);
    void DisplayNoNetwork(String message);

}
