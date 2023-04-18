package com.example.shopproject.view;

import com.example.shopproject.mode.Province;
import com.example.shopproject.mode.ShippingAddress;

import java.util.List;

public interface EditAddressView {
    void DisplayProvinceSuccess(List<Province> mList);
    void DisplayErrorName(String error);
    void DisplayErrorSDT(String error);
    void DisplayErrorAddress(String error);
    void PassshippingAddress(ShippingAddress shippingAddress);
    void DisplayError(String message);
}
