package com.example.shopproject.presenter;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.PriceComparaterHighLow;
import com.example.shopproject.orther_handle.PriceComparaterLowHigh;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.FavoriteProductView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

public class FavoriteProductPresenter implements CallbackProductMode, CallbackUserMode {

    private Context mContext;
    private FavoriteProductView mFavoriteProductView;
    private ProductInterator mProducInterator;
    private UserInterator userInterator;
    private List<Product> List;
    private User user;

    public FavoriteProductPresenter(Context mContext, FavoriteProductView mFavoriteProductView) {
        this.mContext = mContext;
        this.mFavoriteProductView = mFavoriteProductView;
        this.mProducInterator = new ProductInterator(mContext, this);
        this.userInterator = new UserInterator(mContext, this);
        this.List = new ArrayList<>();
    }

    public void getUser(){
        if(Publics.isNetWorkAvaliable(mContext)){
            String id = ShopProjectDatabase.getInstance(mContext).accountDAO().getIdUser();
            if(id == null)
                return;
            userInterator.getUserById(id);
        }else {
            mFavoriteProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void getListFavoriteProduct(){

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

    public void RefreshData(){
        this.List.clear();
        getUser();
    }

    public void setNumIcon(){
        Log.e("Tri1", "num = " + this.List.size());
    }

    @Override
    public void getProductSuccess(Product product) {

    }

    @Override
    public void getProductSuccessById(Product product) {
        this.List.add(product);
        for(int i = 0; i < this.List.size(); i++){
            Log.e("Tri", "So luong: " + this.List.size());
            Log.e("Tri", this.List.get(i).getName());
        }
        mFavoriteProductView.DisplayListFavoriteProduct(this.List);
    }

    @Override
    public void GetUserSuccess(User user) {
        this.user = user;
        if(Publics.isNetWorkAvaliable(mContext)){
            if(this.user.getFavorites() == null || this.user.getFavorites().isEmpty()){
                mFavoriteProductView.DisplayListFavoriteProduct(null);
                return;
            }
            Log.e("Tri1", "num = " + this.user.getFavorites().size());
            mFavoriteProductView.DisplayNumIcon(this.user.getFavorites().size());
            for(int i = 0; i < this.user.getFavorites().size(); i++){
                mProducInterator.getProductById(this.user.getFavorites().get(i).getProduct());
            }

        }
        else {
            mFavoriteProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void CallbackUserFailure(String message) {
        mFavoriteProductView.DisplayError(message);
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
