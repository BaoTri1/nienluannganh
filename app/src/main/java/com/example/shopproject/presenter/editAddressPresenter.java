package com.example.shopproject.presenter;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.mode.Province;
import com.example.shopproject.mode.ShippingAddress;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackAddress;
import com.example.shopproject.repository.AddressInterator;
import com.example.shopproject.view.EditAddressView;

import java.util.List;

public class editAddressPresenter implements CallbackAddress {

    private Context mContext;
    private EditAddressView editAddressView;
    private AddressInterator addressInterator;
    private ShippingAddress shippingAddress;

    public editAddressPresenter(Context mContext, EditAddressView editAddressView) {
        this.mContext = mContext;
        this.editAddressView = editAddressView;
        this.addressInterator = new AddressInterator(mContext, this);
    }

    public void getAddress(){
        if(Publics.isNetWorkAvaliable(mContext)){
            addressInterator.getAddress();

        }else {
            editAddressView.DisplayError("Đã xảy ra lỗi. Hãy kiểm tra lại kết nối mạng.");
        }
    }

    public void createShippingAddress(String name, String numApartment_streetName, String city, String postalCode, String country, String sdt, String strArea){
        if(name.isEmpty()){
            editAddressView.DisplayErrorName("Họ và tên không được để trống!");
            return;
        }
        editAddressView.DisplayErrorName(null);

        if(sdt.isEmpty()){
            editAddressView.DisplayErrorSDT("SDT không được để trống!");
            return;
        }
        editAddressView.DisplayErrorSDT(null);

        if(numApartment_streetName.isEmpty()){
            editAddressView.DisplayErrorAddress("Số nhà, tên đường không được để trống!");
            return;
        }
        editAddressView.DisplayErrorAddress(null);

        String address = numApartment_streetName + strArea;

        shippingAddress = new ShippingAddress(name, address, city, postalCode, country, sdt);
        Log.e("Tri", "fullName: " + shippingAddress.getFullName() + ", address: " + shippingAddress.getAddress() + ", sdt: " + shippingAddress.getPhone());
        editAddressView.PassshippingAddress(shippingAddress);
    }

    @Override
    public void getAddressSuccess(List<Province> mList) {
        editAddressView.DisplayProvinceSuccess(mList);
    }

    @Override
    public void getDataFailue(String message) {
        editAddressView.DisplayError(message);
    }
}
