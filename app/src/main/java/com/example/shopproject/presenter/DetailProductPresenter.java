package com.example.shopproject.presenter;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.view.menu.MenuBuilder;

import com.example.shopproject.R;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.DetailProductView;

import java.util.ArrayList;
import java.util.List;

public class DetailProductPresenter implements CallbackProductMode, CallbackUserMode {

    private Context mContext;
    private DetailProductView mDetailProductView;
    private UserInterator userInterator;
    private ProductInterator mProductInterator;
    private User user;
    private List<String> listIdProductFavorite;
    private Product product;

    public DetailProductPresenter(Context mContext, DetailProductView mDetailProductView) {
        this.mContext = mContext;
        this.mDetailProductView = mDetailProductView;
        this.mProductInterator = new ProductInterator(mContext, this);
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
            mDetailProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void getProduct(String slug){
        if(slug == null)
            return;
        if(Publics.isNetWorkAvaliable(mContext)){
            mProductInterator.getProduct(slug);
        }else {
            mDetailProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void createListProduct(){
        if(Publics.isNetWorkAvaliable(mContext)){
            mProductInterator.getListProducts();
        }
        else {
            mDetailProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void setupBadge(){
        String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
        int number = ShopProjectDatabase.getInstance(mContext).itemCartDAO().getNumItemCart(email);
        mDetailProductView.DisplayBadge(number);
    }

    public void addFavoriteProduct(String idProduct){
        String id = ShopProjectDatabase.getInstance(mContext).accountDAO().getIdUser();
        if(id == null)
            return;
        mProductInterator.postFavoriteProduct(id, idProduct);
    }

    public List<String> getListIdProductFavorite(){
        return this.listIdProductFavorite;
    }

    @Override
    public void getProductSuccess(Product product) {
        mDetailProductView.DisplayInforProduct(product);
        mDetailProductView.DisplayReviews(product.getRating(), product.getNumReviews(), product.getReviews());
    }

    @Override
    public void getImageProductSuccess(List<Photo> listimg) {
        mDetailProductView.DisplayImagesProduct(listimg);
    }

    @Override
    public void getListProductSuccess(List<Product> mlist) {
        mDetailProductView.DisplayListProduct(mlist, this.listIdProductFavorite);
    }

    @Override
    public void getDataFailure(String message) {
        mDetailProductView.DisplayError(message);
    }

    @Override
    public void CallbackUserFailure(String message) {
        mDetailProductView.DisplayError(message);
    }

    @Override
    public void GetUserSuccess(User user) {
        this.user = user;
        if(Publics.isNetWorkAvaliable(mContext)){
            if(this.user.getFavorites() == null || this.user.getFavorites().isEmpty()){
                return;
            }
            for(int i = 0; i < this.user.getFavorites().size(); i++){
                mProductInterator.getProductById(this.user.getFavorites().get(i).getProduct());
            }
        }
        else {
            mDetailProductView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void getProductSuccessById(Product product) {
        this.listIdProductFavorite.add(product.get_id());
    }
}
