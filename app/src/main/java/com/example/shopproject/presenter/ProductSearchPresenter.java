package com.example.shopproject.presenter;

import android.content.Context;

import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.ProductView;

import java.util.List;

public class ProductSearchPresenter implements CallbackProductMode {

    private Context mContext;
    private ProductView productView;
    private ProductInterator productInterator;

    public ProductSearchPresenter(Context mContext, ProductView productView) {
        this.mContext = mContext;
        this.productView = productView;
        this.productInterator = new ProductInterator(mContext, this);
    }

    public void SearchProductsByCategory(String category){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.SearchProductsByCategory(category);

        }else {
            productView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void getSearchResponseSuccess(SearchResponse searchResponse) {
        productView.DisplayListProduct(searchResponse.getProducts());
        productView.DisplayNumProducts(searchResponse.getCountProducts() + " sản phẩm");
    }

    @Override
    public void getDataFailure(String message) {
        productView.DisplayError(message);
    }
}
