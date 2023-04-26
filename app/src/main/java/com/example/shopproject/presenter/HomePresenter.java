package com.example.shopproject.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.shopproject.mode.FavoriteResponse;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.favorites;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.HomeView;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements CallbackProductMode, CallbackUserMode {

    private Context mContext;
    private HomeView mHomeView;
    private UserInterator userInterator;
    private ProductInterator mProductInterator;
    private User user;
    private List<String> listIdProductFavorite;

    public HomePresenter(Context mContext, HomeView mHomeView) {
        this.mContext = mContext;
        this.mHomeView = mHomeView;
        mProductInterator = new ProductInterator(mContext, this);
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
            mHomeView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
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

    public void addFavoriteProduct(String idProduct){
        String id = ShopProjectDatabase.getInstance(mContext).accountDAO().getIdUser();
        if(id == null)
            return;
        mProductInterator.postFavoriteProduct(id, idProduct);
    }

    @Override
    public void CallbackUserFailure(String message) {
        mHomeView.DisplayError(message);
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
            mHomeView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void postFavoriteProductSuccess(FavoriteResponse response) {
        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
        mHomeView.DisplayNumIcon(response.getFavorites().size());
    }

    @Override
    public void getProductSuccessById(Product product) {
        this.listIdProductFavorite.add(product.get_id());
    }

    @Override
    public void getProductSuccess(Product product) {

    }

    @Override
    public void getListProductSuccess(List<Product> mlist) {
        mHomeView.DisplayListProduct(mlist, listIdProductFavorite);
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
