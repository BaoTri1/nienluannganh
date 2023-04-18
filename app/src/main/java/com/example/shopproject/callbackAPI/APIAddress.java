package com.example.shopproject.callbackAPI;

import com.example.shopproject.mode.District;
import com.example.shopproject.mode.Province;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface APIAddress {

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
//            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor);

    APIAddress apiAddress = new Retrofit.Builder()
            .baseUrl("https://provinces.open-api.vn/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okBuilder.build())
            .build()
            .create(APIAddress.class);

    @GET("api/?depth=3")
    Call<List<Province>> getAddress();

}
