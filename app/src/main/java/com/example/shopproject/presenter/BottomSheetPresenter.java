package com.example.shopproject.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.Product;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.BottomSheetView;
import com.example.shopproject.view.UI.Fragment.CartFragment;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.UI.MainActivity;

public class BottomSheetPresenter implements CallbackProductMode {

    private Context mContext;
    private BottomSheetView bottomSheetView;
    private ProductInterator productInterator;
    private int numberIncreased;
    private int numberfillin;
    private boolean check = false;
    private Product product;

    public BottomSheetPresenter(Context mContext, BottomSheetView bottomSheetView) {
        this.mContext = mContext;
        this.bottomSheetView = bottomSheetView;
        this.productInterator = new ProductInterator(mContext, this);
    }

    public void getProduct(String slug){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getProduct(slug);
        }
        else {
            bottomSheetView.DisplayError("Đã xảy lỗi. Hãy tải lại!");
        }
    }

    public void decrementQuantity(int quantity){
        if(quantity == 1)
            return;
        quantity--;
        bottomSheetView.DisplayQuantity(String.valueOf(quantity));
    }

    public void incrementQuantity(String slug, int quantity, int indexColor, int indexSize){
        this.numberIncreased = quantity + 1;
        if(indexColor == -1){
            productInterator.getCountInStock(slug);
        }else {
            if(indexSize == -1){
                productInterator.getCountColor(slug, indexColor);
            }else {
                productInterator.getCountSize(slug, indexColor, indexSize);
            }
        }
    }

    public void FillinQuantity(String slug, int number, int indexColor, int indexSize){
        numberfillin = number;
        check = true;
        if(indexColor == -1){
            productInterator.getCountInStock(slug);
        }else {
            if(indexSize == -1){
                productInterator.getCountColor(slug, indexColor);
            }else {
                productInterator.getCountSize(slug, indexColor, indexSize);
            }
        }
    }

    public void OpenImageViewer(){
        bottomSheetView.DisplayImgageDialog(this.product.getImage());
    }

    public void addCart(int quantity, int indexColor, int indexSize){
        String slug = this.product.getSlug();
        String name = this.product.getName();
        String image = this.product.getImage();
        int price = this.product.getPrice();
        String _id = this.product.get_id();

        Items items = new Items(slug, name, quantity, image, price, _id, indexColor, indexSize);
        bottomSheetView.PassItemCart(items);
    }

    @Override
    public void getNumProductAvailable(int number) {
        if(!check){
            if(numberIncreased > number){
                bottomSheetView.DisplayQuantityError("Sản phẩm đã hết hàng");
            }
            else {
                bottomSheetView.DisplayQuantity(String.valueOf(numberIncreased));
            }
        }else {
            if(numberfillin > number){
                bottomSheetView.DisplayQuantityError("Đã vượt quá số lượng hiện có");
                bottomSheetView.DisplayQuantity(String.valueOf(number));
            }else {
                bottomSheetView.DisplayQuantity(String.valueOf(numberfillin));
            }
        }
        check = false;
    }

    @Override
    public void getProductSuccess(Product product) {
        this.product = product;
        bottomSheetView.DisplayProduct(product);
        bottomSheetView.DisplayColorsAndSize(product.getColor());
    }

    @Override
    public void getDataFailure(String message) {
        bottomSheetView.DisplayError("Đã xảy lỗi. Hãy tải lại!");
    }
}
