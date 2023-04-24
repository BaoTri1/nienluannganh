package com.example.shopproject.presenter;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.shopproject.R;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements CallbackProductMode {

    private Context mContext;
    private HomeView mHomeView;
    private ProductInterator mProductInterator;

    public HomePresenter(Context mContext, HomeView mHomeView) {
        this.mContext = mContext;
        this.mHomeView = mHomeView;
        mProductInterator = new ProductInterator(mContext, this);
    }

    public void createBanner(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo("https://intphcm.com/data/upload/banner-thoi-trang4.jpg"));
        list.add(new Photo("https://intphcm.com/data/upload/1608888571-banner-thoi-trang-1.jpg"));
        list.add(new Photo("https://intphcm.com/data/upload/1608888571-banner-thoi-trang-2.jpg"));
        list.add(new Photo("https://intphcm.com/data/upload/1608888571-banner-thoi-trang-3.jpg"));
        mHomeView.DisplayBanner(list);
    }

    public void createListCatalog(){
        if(Publics.isNetWorkAvaliable(mContext)){
            mProductInterator.getListCatalogs();
        }
        else {
            mHomeView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void createListProducts(){
        if(Publics.isNetWorkAvaliable(mContext)){
            mProductInterator.getListProducts();
        }
        else {
            mHomeView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    @Override
    public void getListProductSuccess(List<Product> mlist) {
        mHomeView.DisplayListProduct(mlist);
    }

    @Override
    public void getListCatalogsSuccess(List<String> listCatalogs) {
        mHomeView.DisplayCatalog(listCatalogs);
    }

    @Override
    public void getDataFailure(String message) {
        mHomeView.DisplayError(message);
    }
}
