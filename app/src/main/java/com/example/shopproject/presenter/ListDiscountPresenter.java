package com.example.shopproject.presenter;

import android.content.Context;

import com.example.shopproject.mode.Discount;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.ListDiscountView;
import com.example.shopproject.view.ListordersHistoryView;

import java.util.List;

public class ListDiscountPresenter implements CallbackProductMode {

    private Context mContext;
    private ListDiscountView listDiscountView;
    private ProductInterator productInterator;

    public ListDiscountPresenter(Context mContext, ListDiscountView listDiscountView) {
        this.mContext = mContext;
        this.listDiscountView = listDiscountView;
        this.productInterator = new ProductInterator(mContext, this);
    }

    public void getListDiscount(){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getListDiscount();
        }else {
            listDiscountView.DisplayError("Đã xảy ra lỗi. Vui lòng kiểm tra kết nối mạng!");
        }
    }

    @Override
    public void getDataFailure(String message) {
        listDiscountView.DisplayError(message);
    }

    @Override
    public void getListDiscountSuccess(List<Discount> mList) {
        listDiscountView.DislayListOrders(mList);
    }
}
