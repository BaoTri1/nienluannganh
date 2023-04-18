package com.example.shopproject.presenter.callbackMode;

import com.example.shopproject.mode.Province;

import java.util.List;

public interface CallbackAddress {
    void getAddressSuccess(List<Province> mList);
    void getDataFailue(String message);
}
