package com.example.shopproject.presenter;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.mode.FavoriteResponse;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.ProductView;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchPresenter implements CallbackProductMode, CallbackUserMode {

    private Context mContext;
    private ProductView productView;
    private ProductInterator productInterator;
    private UserInterator userInterator;
    private User user;
    private List<String> listIdProductFavorite;

    public ProductSearchPresenter(Context mContext, ProductView productView) {
        this.mContext = mContext;
        this.productView = productView;
        this.productInterator = new ProductInterator(mContext, this);
        this.userInterator = new UserInterator(mContext, this);
        this.listIdProductFavorite = new ArrayList<>();
    }

    public void getUser(){
        if(Publics.isNetWorkAvaliable(mContext)){
            String id = ShopProjectDatabase.getInstance(mContext).accountDAO().getIdUser();
            if(id == null)
                return;
            userInterator.getUserById(id);
        }else {
            productView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void SearchProductsByCategory(String category){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.SearchProductsByCategory(category);

        }else {
            productView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    public void SearchProductByQuery(String query){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.SearchProductsByQuery(query);

        }else {
            productView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    public void getListProductFilter(String category, String query, String price, String rating, String order){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getListProductFilter(category, query, price, rating, order);

        }else {
            productView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    public void setupBadge(){
        String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
        int number = ShopProjectDatabase.getInstance(mContext).itemCartDAO().getNumItemCart(email);
        productView.DisplayBadge(number);
    }

    public void addFavoriteProduct(String idProduct){
        String id = ShopProjectDatabase.getInstance(mContext).accountDAO().getIdUser();
        if(id == null)
            return;
        productInterator.postFavoriteProduct(id, idProduct);
    }

    @Override
    public void getSearchResponseSuccess(SearchResponse searchResponse) {
        productView.DisplayListProduct(searchResponse.getProducts(), this.listIdProductFavorite);
        productView.DisplayNumProducts(searchResponse.getCountProducts() + " sản phẩm");
    }

    @Override
    public void getDataFailure(String message) {
        productView.DisplayError(message);
    }

    @Override
    public void CallbackUserFailure(String message) {
        productView.DisplayError(message);
    }

    @Override
    public void GetUserSuccess(User user) {
        this.user = user;
        if(Publics.isNetWorkAvaliable(mContext)){
            if(this.user.getFavorites() == null || this.user.getFavorites().isEmpty()){
                return;
            }
            for(int i = 0; i < this.user.getFavorites().size(); i++){
                productInterator.getProductById(this.user.getFavorites().get(i).getProduct());
            }
        }
        else {
            productView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void getProductSuccessById(Product product) {
        this.listIdProductFavorite.add(product.get_id());
    }

    @Override
    public void postFavoriteProductSuccess(FavoriteResponse response) {
        CallbackProductMode.super.postFavoriteProductSuccess(response);
    }
}
