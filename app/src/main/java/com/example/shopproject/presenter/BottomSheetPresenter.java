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
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.sqlite.Entity.itemCart;
import com.example.shopproject.view.BottomSheetView;
import com.example.shopproject.view.UI.Fragment.CartFragment;
import com.example.shopproject.view.UI.Fragment.callback.CallbackFragment;
import com.example.shopproject.view.UI.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetPresenter implements CallbackProductMode {

    private Context mContext;
    private BottomSheetView bottomSheetView;
    private ProductInterator productInterator;
    private int numberIncreased;
    private int numberfillin;
    private int numberUpdate;
    private int check = 0;
    private Product product;
    private String email;
    private String size;

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
        check = 1;
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

    public void saveItemCart(int quantity, String color, String size, int indexColor, int indexSize){
        String slug = this.product.getSlug();
        String name = this.product.getName();
        String image = this.product.getImage();
        int price = this.product.getPrice();
        String _id = this.product.get_id();

        email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
        this.size = size;
        itemCart itemCartTmp = ShopProjectDatabase.getInstance(mContext).itemCartDAO().getItemCartBySlug(email, slug, size);
        if(itemCartTmp == null){
            itemCart itemCart = new itemCart(email, slug, name, quantity, image, price, _id, indexColor, indexSize, color, size);
            ShopProjectDatabase.getInstance(mContext).itemCartDAO().insertItemCart(itemCart);
        }else {
            Log.e("Tri", "đã tồn tại: " + itemCartTmp.getSlug());
            numberUpdate = quantity + itemCartTmp.getQuantity();
            check = 2;
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

    }

    public void addCart(int quantity, String color, String size, int indexColor, int indexSize){
        String slug = this.product.getSlug();
        String name = this.product.getName();
        String image = this.product.getImage();
        int price = this.product.getPrice();
        String _id = this.product.get_id();

        Items items = new Items(slug, name, quantity, image, price, _id, indexColor, indexSize, color, size);
        bottomSheetView.PassItemCart(items);
    }

    public void createItemOrder(int quantity, String color, String size, int indexColor, int indexSize){
        String slug = this.product.getSlug();
        String name = this.product.getName();
        String image = this.product.getImage();
        int price = this.product.getPrice();
        String _id = this.product.get_id();

        Items items = new Items(slug, name, quantity, image, price, _id, indexColor, indexSize, color, size);
        List<Items> mListItems = new ArrayList<>();
        mListItems.add(items);
        bottomSheetView.PassItemOrder(mListItems);
    }

    @Override
    public void getNumProductAvailable(int number) {
        if(check == 0){
            if(numberIncreased > number){
                bottomSheetView.DisplayQuantityError("Sản phẩm đã hết hàng");
            }
            else {
                bottomSheetView.DisplayQuantity(String.valueOf(numberIncreased));
            }
        }else if(check == 1){
            if(numberfillin > number){
                bottomSheetView.DisplayQuantityError("Đã vượt quá số lượng hiện có");
                bottomSheetView.DisplayQuantity(String.valueOf(number));
            }else {
                bottomSheetView.DisplayQuantity(String.valueOf(numberfillin));
            }
            check = 0;
        }else if(check == 2){
            if(numberUpdate > number){
                ShopProjectDatabase.getInstance(mContext).itemCartDAO().updateQuantityItemCart(email, this.product.getSlug(), this.size, number);
            }else {
                ShopProjectDatabase.getInstance(mContext).itemCartDAO().updateQuantityItemCart(email, this.product.getSlug(), this.size, numberUpdate);
            }
            check = 0;
        }
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
