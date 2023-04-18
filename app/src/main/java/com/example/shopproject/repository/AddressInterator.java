package com.example.shopproject.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.example.shopproject.callbackAPI.APIAddress;
import com.example.shopproject.mode.District;
import com.example.shopproject.mode.Province;
import com.example.shopproject.presenter.callbackMode.CallbackAddress;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressInterator implements AddressRepository{

    private Context context;
    private CallbackAddress callbackAddress;
    private ProgressDialog progressDialog;


    public AddressInterator(Context context, CallbackAddress callbackAddress) {
        this.context = context;
        this.callbackAddress = callbackAddress;
        this.progressDialog = new ProgressDialog(context);
    }

    @Override
    public void getAddress() {
        progressDialog.show();
        progressDialog.setMessage("Đang tải dữ liệu...");
        APIAddress.apiAddress.getAddress().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if(response.isSuccessful()){
                    List<Province> result = response.body();
                    callbackAddress.getAddressSuccess(result);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 1000);
                }else {
                    callbackAddress.getDataFailue("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                callbackAddress.getDataFailue("Đã xảy ra lỗi do call api thất bại");
            }
        });
    }
}
