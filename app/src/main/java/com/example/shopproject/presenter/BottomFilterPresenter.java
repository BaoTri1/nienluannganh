package com.example.shopproject.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.BottomFilterView;
import com.example.shopproject.view.ProductView;

import java.util.List;

public class BottomFilterPresenter implements CallbackProductMode {

    private Context mContext;
    private BottomFilterView bottomFilterView;
    private ProductInterator productInterator;
    private SharedPreferences pfrFilter;

    public BottomFilterPresenter(Context mContext, BottomFilterView bottomFilterView) {
        this.mContext = mContext;
        this.bottomFilterView = bottomFilterView;
        this.productInterator = new ProductInterator(mContext, this);
        this.pfrFilter = mContext.getSharedPreferences("Filter", Context.MODE_PRIVATE);
    }

    public void createListCatalog(){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getListCatalogs();
        }
        else {
            bottomFilterView.DisplayError("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    @Override
    public void getListCatalogsSuccess(List<String> listCatalogs) {
        bottomFilterView.DisplayCategorySuccess(listCatalogs);
    }

    public void saveFilter(String category, String price, String rate){
        SharedPreferences.Editor editor = pfrFilter.edit();
        editor.putString("Category", category);
        editor.putString("Price", price);
        editor.putString("Rate", rate);
        editor.apply();
    }

    public void clearFilter(){
        SharedPreferences.Editor editor = pfrFilter.edit();
        editor.putString("Category", "");
        editor.putString("Price", "");
        editor.putString("Rate", "");
        editor.apply();
    }

    public void getFilter(){
        String category = pfrFilter.getString("Category", "");
        String price = pfrFilter.getString("Price", "");
        String rate = pfrFilter.getString("Rate", "");
        bottomFilterView.DisplayFilter(category, price, rate);
    }
}
