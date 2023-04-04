package com.example.shopproject.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.view.LoginView;

import java.util.regex.Pattern;

public class LoginPresenter implements CallbackUserMode {

    private final LoginView mLoginView;
    private final UserInterator mUserInterator;
    private Context mContext;

    public LoginPresenter(LoginView mLoginView, Context mContext) {
        this.mLoginView = mLoginView;
        this.mContext = mContext;
        mUserInterator = new UserInterator(mContext, this);
    }

    public void login(String email, String password){
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mLoginView.DisplayMistakeOfEmail("Email không hợp lệ!");
            return;
        }

        mLoginView.DisplayMistakeOfEmail(null);

        if(TextUtils.isEmpty(password)){
            mLoginView.DisplayMistakeOfPasswd("Password không để trống!");
            return;
        }

        mLoginView.DisplayMistakeOfPasswd(null);

        if(Publics.isNetWorkAvaliable(mContext)){
            mUserInterator.LoginRequest(email, password);
        }else {
            mLoginView.DisplayLoginFailue("Đăng nhập thất bại. Kiểm tra lại kết nối mạng!");
        }
    }

    @Override
    public void LoginSuccess(User user) {
        mLoginView.DisplayLoginSuccess(user);
        Publics.SaveToken(mContext, user.getToken());
    }

    @Override
    public void LoginFailure(String message) {
        mLoginView.DisplayLoginFailue(message);
    }

    @Override
    public void CallbackUserFailure(String message) {
        mLoginView.DisplayLoginFailue(message);
    }
}
