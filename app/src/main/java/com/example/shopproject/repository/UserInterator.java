package com.example.shopproject.repository;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.callbackAPI.APIService;
import com.example.shopproject.mode.LoginRequest;
import com.example.shopproject.mode.RegisterRequest;
import com.example.shopproject.mode.User;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInterator implements UserRepository {

    private CallbackUserMode callbackUserMode;
    private Context mContext;

    public UserInterator(Context mContext, CallbackUserMode callbackUserMode) {
        this.mContext = mContext;
        this.callbackUserMode = callbackUserMode;
    }

    @Override
    public void LoginRequest(String email, String password) {
        LoginRequest request = new LoginRequest(email, password);
        APIService.apiService.Login(request).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User userResult = response.body();
                    callbackUserMode.LoginSuccess(userResult);
                }
                else {
                    callbackUserMode.LoginFailure("Email hoặc mật khẩu không đúng!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callbackUserMode.CallbackUserFailure("Đăng nhập thất bại!");
            }
        });
    }

    @Override
    public void RegisterRequest(String Name, String email, String password) {
        RegisterRequest registerRequest = new RegisterRequest(Name, email, password);
        APIService.apiService.Register(registerRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User userResult = response.body();
                    callbackUserMode.RegisterSuccess(userResult);
                }else {
                    callbackUserMode.RegisterFailure("Đăng ký thất bại!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callbackUserMode.CallbackUserFailure("Đăng ký thất bại!");
            }
        });
    }

    @Override
    public List<User> getListUser() {
        return null;
    }

}
