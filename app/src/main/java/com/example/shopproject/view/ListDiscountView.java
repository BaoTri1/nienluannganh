package com.example.shopproject.view;

import com.example.shopproject.mode.Discount;
import com.example.shopproject.mode.Orders;

import java.util.List;

public interface ListDiscountView {

    void DislayListOrders(List<Discount> mList);
    void DisplayError(String error);
}
