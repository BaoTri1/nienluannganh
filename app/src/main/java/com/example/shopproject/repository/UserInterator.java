package com.example.shopproject.repository;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.callbackAPI.APIService;
import com.example.shopproject.mode.LoginRequest;
import com.example.shopproject.mode.RegisterRequest;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackUserMode;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInterator implements UserRepository {

    private CallbackUserMode callbackUserMode;
    private Context mContext;
    public final static String tokenAdmin = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2Y1ZmI2YmFiNGNjMjc2MGUwNmFhOTAiLCJuYW1lIjoiQWRtaW4iLCJlbWFpbCI6ImFkbWluQGV4YW1wbGUuY29tIiwiaXNBZG1pbiI6dHJ1ZSwiaWF0IjoxNjgwNTMyMzQ0LCJleHAiOjE2ODMxMjQzNDR9.p6rGg2MgaBvdROGLKT9xFnGip-RQdGeW6_jGtkj2Wpc";

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
    public void getUserById(String id) {
        APIService.apiService.getUserById(tokenAdmin, id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User userResult = response.body();
                    callbackUserMode.GetUserSuccess(userResult);
                }else {
                    callbackUserMode.GetUserFailure("Lấy thông tin thất bại!");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callbackUserMode.GetUserFailure("Lấy thông tin thất bại do call api thất bại!");
            }
        });
    }

}
