package com.example.shopproject.presenter;

import android.content.Context;

import com.example.shopproject.mode.Orders;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.ListordersHistoryView;

import java.util.List;

public class ListordersHistoryPresenter implements CallbackProductMode {

    private Context mContext;
    private ListordersHistoryView listordersHistoryView;
    private ProductInterator productInterator;

    public ListordersHistoryPresenter(Context mContext, ListordersHistoryView listordersHistoryView) {
        this.mContext = mContext;
        this.listordersHistoryView = listordersHistoryView;
        this.productInterator = new ProductInterator(mContext, this);
    }

    public void getListOrderHistory(){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getListOrdersHistory();
        }else {
            listordersHistoryView.DisplayError("Đã xảy ra lỗi. Vui lòng kiểm tra kết nối mạng!");
        }
    }

    @Override
    public void getDataFailure(String message) {
        listordersHistoryView.DisplayError(message);
    }

    @Override
    public void getListOrdersHistorySuccess(List<Orders> mList) {
        listordersHistoryView.DislayListOrders(mList);
    }
}
