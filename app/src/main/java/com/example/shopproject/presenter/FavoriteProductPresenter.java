package com.example.shopproject.presenter;

import android.content.Context;

import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.PriceComparaterHighLow;
import com.example.shopproject.orther_handle.PriceComparaterLowHigh;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.FavoriteProductView;

import java.util.Collections;
import java.util.List;

public class FavoriteProductPresenter implements CallbackProductMode {

    private Context mContext;
    private FavoriteProductView mFavoriteProductView;
    private ProductInterator mProducInterator;
    private List<Product> List;


    public FavoriteProductPresenter(Context mContext, FavoriteProductView mFavoriteProductView) {
        this.mContext = mContext;
        this.mFavoriteProductView = mFavoriteProductView;
        this.mProducInterator = new ProductInterator(mContext, this);
    }

    public void getListFavoriteProduct(){
        if(Publics.isNetWorkAvaliable(mContext)){
            mProducInterator.getListFavoriteProducts();
        }
        else {
            mFavoriteProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    public void SortList(String typesort){
        if (List == null)
            return;

        if(typesort.equals("HIGH_LOW")){
            Collections.sort(List, new PriceComparaterHighLow());
        }
        else {
            Collections.sort(List, new PriceComparaterLowHigh());
        }
        mFavoriteProductView.DisplaySortList(List);
    }

    public void deleteProdut(Product product){
        List.remove(product);
        mFavoriteProductView.DisplayChangeList(List);
    }

    @Override
    public void getListFavoriteProductSuccess(List<Product> mlist) {
        mFavoriteProductView.DisplayListFavoriteProduct(mlist);
        this.List = mlist;
    }

    @Override
    public void ListFavoriteProductisEmpty() {
        mFavoriteProductView.DisplayisListEmpty();
    }

    @Override
    public void getDataFailure(String message) {
        mFavoriteProductView.DisplayError(message);
    }
}
