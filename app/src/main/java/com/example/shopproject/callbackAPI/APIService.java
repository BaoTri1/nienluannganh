package com.example.shopproject.callbackAPI;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.shopproject.mode.LoginRequest;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.RegisterRequest;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.mode.User;
import com.example.shopproject.orther_handle.Publics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

//    // Add Headers vao cho cac link API
//    Interceptor interceptor = chain -> {
//        //Đọc Authorization từ SharePreferent lên
//
//        Request request = chain.request();
//        Request.Builder builder = request.newBuilder();
//        builder.addHeader("Authorization", Authorization);
//        return chain.proceed(builder.build());
//    };

    //Add Logging
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .connectTimeout(3, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
//            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor);

    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.161:5000/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okBuilder.build())
            .build()
            .create(APIService.class);
    
    @POST("api/users/signin/")
    Call<User> Login(@Body LoginRequest loginRequest);

    @POST("api/users/signup/")
    Call<User> Register(@Body RegisterRequest registerRequest);

    @GET("api/users/")
    Call<List<User>> getListUser();

    @GET("/api/products/categories")
    Call<List<String>> getListCatalogs();

    @GET("/api/products")
    Call<List<Product>> getListProducts();

    @GET("/api/products/slug/{slug}")
    Call<Product> getProduct(@Path("slug") String slug);

    @GET("/api/products/{id}")
    Call<Product> getProductById(@Path("id") String id);

    @GET("/api/products/search")
    Call<SearchResponse> searchProductByCategory(@Query("category") String category);

}


