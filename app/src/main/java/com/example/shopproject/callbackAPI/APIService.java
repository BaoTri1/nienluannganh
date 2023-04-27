package com.example.shopproject.callbackAPI;

import com.example.shopproject.mode.Discount;
import com.example.shopproject.mode.FavoriteRequest;
import com.example.shopproject.mode.FavoriteResponse;
import com.example.shopproject.mode.LoginRequest;
import com.example.shopproject.mode.OrderRequest;
import com.example.shopproject.mode.Orders;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.favorites;
import com.example.shopproject.mode.RegisterRequest;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.mode.User;
import com.example.shopproject.mode.orderResponse;
import com.example.shopproject.mode.reviewRequest;
import com.example.shopproject.mode.reviewResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("api/users/{id}")
    Call<User> getUserById(@Header("authorization") String header, @Path("id") String id);

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

    @GET("/api/products/search")
    Call<SearchResponse> searchProductByQuery(@Query("query") String query);

    @GET("/api/products/search")
    Call<SearchResponse> FilterProduct(@Query("category") String category,
                                       @Query("query") String query,
                                       @Query("price") String price,
                                       @Query("rating") String rating,
                                       @Query("order") String order);

    @POST("/api/orders")
    Call<orderResponse> postOrder(@Header("authorization") String header, @Body OrderRequest orderRequest);

    @POST("/api/products/{id}/reviews")
    Call<reviewResponse> postReview(@Header("authorization") String header, @Path("id") String id, @Body reviewRequest reviewRequest);

    @GET("/api/orders/mine")
    Call<List<Orders>> getListOrderHistory(@Header("authorization") String header);

    @POST("/api/users/favorite/")
    Call<FavoriteResponse> postFavariteProduct(@Header("authorization") String header, @Body FavoriteRequest favoriteResponse);

    @GET("/api/discount/")
    Call<List<Discount>> getListDiscount();

}


