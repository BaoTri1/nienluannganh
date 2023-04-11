package com.example.shopproject.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.shopproject.R;
import com.example.shopproject.callbackAPI.APIService;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductInterator implements ProductRepository{

    private Context mContext;
    private CallbackProductMode callbackProductMode;
    private ProgressDialog progressDialog;

    public ProductInterator(Context mContext, CallbackProductMode callbackProductMode) {
        this.mContext = mContext;
        this.callbackProductMode = callbackProductMode;
        this.progressDialog = new ProgressDialog(mContext);
    }

    @Override
    public void getListProducts() {
        progressDialog.show();
        progressDialog.setMessage("Đang tải dữ liệu...");
        APIService.apiService.getListProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> listResult = response.body();
                    callbackProductMode.getListProductSuccess(listResult);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    }, 1000);
                }
                else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getListCatalogs() {
        APIService.apiService.getListCatalogs().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful()){
                    List<String> listResult = response.body();
                    callbackProductMode.getListCatalogsSuccess(listResult);
                }
                else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getProduct(String slug) {
        APIService.apiService.getProduct(slug).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product productResult = response.body();
                    callbackProductMode.getProductSuccess(productResult);
                }
                else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getListFavoriteProducts() {
        APIService.apiService.getListProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> listResult = response.body();
                    if(listResult.isEmpty()){
                        callbackProductMode.ListFavoriteProductisEmpty();
                    }
                    else {
                        callbackProductMode.getListFavoriteProductSuccess(listResult);
                    }
                }
                else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getCountSize(String slug, int indexColor, int indexSize) {
        APIService.apiService.getProduct(slug).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    callbackProductMode.getNumProductAvailable(product.getColor().get(indexColor).getSize().get(indexSize).getCountSize());
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getCountColor(String slug, int indexColor) {
        APIService.apiService.getProduct(slug).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    callbackProductMode.getNumProductAvailable(product.getColor().get(indexColor).getCountColor());
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getCountInStock(String slug) {
        APIService.apiService.getProduct(slug).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    Product product = response.body();
                    callbackProductMode.getNumProductAvailable(product.getCountInStock());
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }


    @Override
    public void SearchProductsByCategory(String category) {
        APIService.apiService.searchProductByCategory(category).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.isSuccessful()){
                    SearchResponse result = response.body();
                    callbackProductMode.getSearchResponseSuccess(result);
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void SearchProductsByQuery(String query) {
        APIService.apiService.searchProductByQuery(query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.isSuccessful()){
                    SearchResponse result = response.body();
                    callbackProductMode.getSearchResponseSuccess(result);
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getListProductFilter(String category, String query, String price, String rating, String order) {
        APIService.apiService.FilterProduct(category, query, price, rating, order).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if(response.isSuccessful()){
                    SearchResponse result = response.body();
                    callbackProductMode.getSearchResponseSuccess(result);
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
            }
        });
    }

    @Override
    public void getListCart() {
        List<Items> list = new ArrayList<>();
        callbackProductMode.getListCartSuccess(list);
    }

}
