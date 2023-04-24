package com.example.shopproject.presenter;

import android.content.Context;

import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.ProfileView;

public class ProfilePresenter implements CallbackUserMode {

    private Context context;
    private ProfileView profileView;
    private UserInterator userInterator;

    public ProfilePresenter(Context context, ProfileView profileView) {
        this.context = context;
        this.profileView = profileView;
        this.userInterator = new UserInterator(context, this);
    }

    public void getUser(){
        if(Publics.isNetWorkAvaliable(context)){
            String id = ShopProjectDatabase.getInstance(context).accountDAO().getIdUser();
            if(id == null)
                return;
            userInterator.getUserById(id);
        }else {
            profileView.DisplayError("Đã xảy ra lỗi. Vui lòng kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void GetUserSuccess(User user) {
        profileView.DisplayUserSuccess(user);
    }

    @Override
    public void GetUserFailure(String message) {
        profileView.DisplayError(message);
    }
}
