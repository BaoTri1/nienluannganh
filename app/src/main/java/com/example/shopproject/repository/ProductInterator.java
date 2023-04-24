package com.example.shopproject.repository;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.shopproject.R;
import com.example.shopproject.callbackAPI.APIService;
import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.OrderRequest;
import com.example.shopproject.mode.Orders;
import com.example.shopproject.mode.Photo;
import com.example.shopproject.mode.Product;
import com.example.shopproject.mode.SearchResponse;
import com.example.shopproject.mode.ShippingAddress;
import com.example.shopproject.mode.orderResponse;
import com.example.shopproject.mode.reviewRequest;
import com.example.shopproject.mode.reviewResponse;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.sqlite.Entity.itemCart;

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

                    List<Photo> list = new ArrayList<>();
                    list.add(new Photo(productResult.getImage()));
                    for(int i = 0; i < productResult.getColor().size(); i++){
                        for(int j = 0; j < productResult.getColor().get(i).getImage().size(); j++){
                            list.add(new Photo(productResult.getColor().get(i).getImage().get(j)));
                        }
                    }
                    callbackProductMode.getImageProductSuccess(list);
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
                    callbackProductMode.getNumProductAvailable(product.getColor().get(indexColor).getSizes().get(indexSize).getCountSize());
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
        String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
        Log.e("Tri", "vô đây mà ở ngoài");
        List<itemCart> listItemCart = ShopProjectDatabase.getInstance(mContext).itemCartDAO().getListItemCart(email);
        for(int i = 0 ;i< listItemCart.size(); i++){
            String slug = listItemCart.get(i).getSlug();
            String name = listItemCart.get(i).getName();
            String image = listItemCart.get(i).getImage();
            int price = listItemCart.get(i).getPrice();
            String _id = listItemCart.get(i).get_id();
            int quantity = listItemCart.get(i).getQuantity();
            int indexColor = listItemCart.get(i).getIndexColor();
            int indexSize = listItemCart.get(i).getIndexSize();
            String color = listItemCart.get(i).getColor();
            String size = listItemCart.get(i).getSize();

            Log.e("Tri", "vô đây ");

            Items items = new Items(slug, name, quantity, image, price, _id, indexColor, indexSize, color, size);
            list.add(items);
        }
        callbackProductMode.getListCartSuccess(list);
    }

    @Override
    public void PostOrders(List<Items> items, ShippingAddress shippingAddress, String paymentMethod, int toltalPriceProcuct, int shippingPrice, int taxPrice, int toltalPayment) {
        Log.e("Tri", "post order nhung o ngoai");
        OrderRequest request = new OrderRequest(items, shippingAddress, paymentMethod, toltalPriceProcuct, shippingPrice, 0, toltalPayment);
        APIService.apiService.postOrder(Publics.GetToken(mContext), request).enqueue(new Callback<orderResponse>() {
            @Override
            public void onResponse(Call<orderResponse> call, Response<orderResponse> response) {
                if(response.isSuccessful()){
                    orderResponse result = response.body();
                    Log.e("Tri", "post order");
                    callbackProductMode.ordersSuccess(result);
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<orderResponse> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi. Call API thất bại!" + call.toString());
            }
        });
    }

    @Override
    public void PostReview(String comment, int rating, String name, String id) {
        String tokenAdmin = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2Y1ZmI2YmFiNGNjMjc2MGUwNmFhOTAiLCJuYW1lIjoiQWRtaW4iLCJlbWFpbCI6ImFkbWluQGV4YW1wbGUuY29tIiwiaXNBZG1pbiI6dHJ1ZSwiaWF0IjoxNjgwNTMyMzQ0LCJleHAiOjE2ODMxMjQzNDR9.p6rGg2MgaBvdROGLKT9xFnGip-RQdGeW6_jGtkj2Wpc";
        reviewRequest request = new reviewRequest(rating, comment, name);
        APIService.apiService.postReview(Publics.GetToken(mContext), id, request).enqueue(new Callback<reviewResponse>() {
            @Override
            public void onResponse(Call<reviewResponse> call, Response<reviewResponse> response) {
                if(response.isSuccessful()){
                    reviewResponse result = response.body();
                    callbackProductMode.reviewSuccess(result);
                }else {
                    callbackProductMode.getDataFailure("Bạn đã bình luận về sản phẩm này rồi.");
                }
            }

            @Override
            public void onFailure(Call<reviewResponse> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi. Call API thất bại!" + call.toString());
            }
        });
    }

    @Override
    public void getListOrdersHistory() {
        APIService.apiService.getListOrderHistory(Publics.GetToken(mContext)).enqueue(new Callback<List<Orders>>() {
            @Override
            public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                if(response.isSuccessful()){
                    List<Orders> result = response.body();
                    callbackProductMode.getListOrdersHistorySuccess(result);
                }else {
                    callbackProductMode.getDataFailure("Đã xảy ra lỗi.");
                }
            }

            @Override
            public void onFailure(Call<List<Orders>> call, Throwable t) {
                callbackProductMode.getDataFailure("Đã xảy ra lỗi. Lấy dữ liệu thất bại");
            }
        });
    }

}
