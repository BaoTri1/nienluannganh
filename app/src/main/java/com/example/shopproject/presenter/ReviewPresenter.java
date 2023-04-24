package com.example.shopproject.presenter;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.mode.User;
import com.example.shopproject.mode.reviewResponse;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.ReviewView;

public class ReviewPresenter implements CallbackProductMode, CallbackUserMode {

    private Context mContext;
    private ReviewView reviewView;
    private ProductInterator productInterator;
    private UserInterator userInterator;
    private User user;
    private String idProduct;

    public ReviewPresenter(Context mContext, ReviewView reviewView) {
        this.mContext = mContext;
        this.reviewView = reviewView;
        this.productInterator = new ProductInterator(mContext, this);
        this.userInterator = new UserInterator(mContext, this);
    }

    public void ReceiveData(String id){
        this.idProduct = id;
    }

    public void getUser(){
        if(Publics.isNetWorkAvaliable(mContext)){
            String id = ShopProjectDatabase.getInstance(mContext).accountDAO().getIdUser();
            if(id == null)
                return;
            userInterator.getUserById(id);
        }else {
            reviewView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    public void SaveReview(String comment, int rating){
        if(comment.isEmpty()){
            reviewView.DisplayError("Comment không được để trống!");
            return;
        }

        if(rating == 0){
            reviewView.DisplayError("Bạn chưa đáng giá sản phẩm!");
            return;
        }

        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.PostReview(comment, rating, user.getName(), idProduct);
        }
        else {
            reviewView.DisplayNoNetWork("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng.");
        }

    }

    @Override
    public void GetUserSuccess(User user) {
        this.user = user;
    }

    @Override
    public void reviewSuccess(reviewResponse reviewResponse) {
        reviewView.SaveReviewSuccess(reviewResponse.getMessage());
    }

    @Override
    public void getDataFailure(String message) {
        reviewView.DisplayError(message);
    }
}
