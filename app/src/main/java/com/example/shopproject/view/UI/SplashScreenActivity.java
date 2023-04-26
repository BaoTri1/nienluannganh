package com.example.shopproject.view.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopproject.callbackAPI.APIService;
import com.example.shopproject.mode.LoginRequest;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.AccountManagement;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.repository.UserInterator;
import com.example.shopproject.sqlite.Entity.Account;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    private User user;
    private String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        message = "";
        user = null;
        Account account = AccountManagement.getAccount(this);
        if(account != null){
            Toast.makeText(this, "có Account", Toast.LENGTH_SHORT).show();
//            AccountManagement.setFalseLoginState(SplashScreenActivity.this, account.getEmail(), true);
            APIService.apiService.Login(new LoginRequest(account.getEmail(), account.getPasswd())).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        user = response.body();
                        Log.e("Tri", user.getToken());
                        Publics.SaveToken(SplashScreenActivity.this, user.getToken());
                        Log.e("Tri", "Token da luu: " + Publics.GetToken(SplashScreenActivity.this));
                        AccountManagement.setFalseLoginState(SplashScreenActivity.this, account.getEmail(), true);
                    }else {
                        message = "Đã xảy ra lỗi khi đăng nhập";
                        AccountManagement.setFalseLoginState(SplashScreenActivity.this, account.getEmail(), false);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    message = "Đã xảy ra lỗi khi đăng nhập";
                    AccountManagement.setFalseLoginState(SplashScreenActivity.this, account.getEmail(), false);
                }
            });
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ACTION_KEY", "LOGIN");
                bundle.putString("MESSAGE_KEY", message);
                bundle.putSerializable("USER_KEY", user);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }
}
