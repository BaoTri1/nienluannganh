package com.example.shopproject.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.view.RegisterView;


public class RegisterPresenter implements CallbackUserMode {

    private final RegisterView mRegisterView;
    private final Context mContext;
    private final UserInterator mUserInterator;

    public RegisterPresenter(Context mContext, RegisterView mRegisterView) {
        this.mContext = mContext;
        this.mRegisterView = mRegisterView;
        mUserInterator = new UserInterator(mContext, this);
    }

    public void Register(String name, String email, String passwd, String ConfirmPasswd){
        if(TextUtils.isEmpty(name)){
           mRegisterView.DisplayMistakeOfName("Họ tên không hợp lệ!");
           return;
        }
        mRegisterView.DisplayMistakeOfName(null);
        if(TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mRegisterView.DisplayMistakeOfEmail("Email không hợp lệ!");
            return;
        }
        mRegisterView.DisplayMistakeOfEmail(null);
        if(TextUtils.isEmpty(passwd)){
            mRegisterView.DisplayMistakeOfPasswd("Trường Mật khẩu không để trống!");
            return;
        }
        mRegisterView.DisplayMistakeOfPasswd(null);
        if(TextUtils.isEmpty(ConfirmPasswd) || !passwd.equals(ConfirmPasswd)){
            mRegisterView.DisplayMistakeOfConfirmPasswd("Mật khẩu không trùng khớp!");
        } else{
            mRegisterView.DisplayMistakeOfConfirmPasswd(null);
            if(Publics.isNetWorkAvaliable(mContext)){
                mUserInterator.RegisterRequest(name, email, passwd);
            }else {
                mRegisterView.DisplayRegisterFailue("Đăng ký thất bại. Kiểm tra lại kết nối mạng!");
            }
        }
    }

    @Override
    public void RegisterSuccess(User user) {
        mRegisterView.DisplayRegisterSuccess(user);
        Publics.SaveToken(mContext, user.getToken());
    }

    @Override
    public void RegisterFailure(String message) {
        mRegisterView.DisplayRegisterFailue(message);
    }

    @Override
    public void CallbackUserFailure(String message) {
        mRegisterView.DisplayRegisterFailue(message);
    }
}
