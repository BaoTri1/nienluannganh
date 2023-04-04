package com.example.shopproject.presenter;

import android.content.ClipData;
import android.content.Context;
import android.util.Log;

import com.example.shopproject.mode.Items;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.view.CartView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CartPresenter implements CallbackProductMode {

    private Context mContext;
    private CartView cartView;
    private ProductInterator productInterator;
    private List<Items> mList;
    private int numberIncreased;

    public CartPresenter(Context mContext, CartView cartView) {
        this.mContext = mContext;
        this.cartView = cartView;
        productInterator = new ProductInterator(mContext, this);
        mList = new ArrayList<>();
    }

    public void getListCart(){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getListCart();
            Log.e("Tri", " cartPresenter");
        }else {
            cartView.DisplayNoNetWork("Đã xảy ra lỗi. Hãy kiểm tra lại kết nối mạng!");
        }
    }

    public void updateListCart(Items items){
        if(mList.isEmpty()) {
            this.mList.add(items);
        }else{
            if(!checkItems(items)){
                this.mList.add(items);
            }else{
                updatequantity(items);
            }
        }
        cartView.DisplayNumProduct(mList.size());
        cartView.DisplaytotlalPrice(ToltalPrice(mList));
        cartView.DisplayListCartUdated(mList);
        Log.e("Tri", items.getName() + " cartPresenter");
    }

    public boolean checkItems(Items items){
        for(int i  = 0; i < this.mList.size(); i++){
            if(Objects.equals(mList.get(i).get_id(), items.get_id()))
                return true;
        }
        return false;
    }

    public void updatequantity(Items items){
        for(int i  = 0; i < this.mList.size(); i++){
            Log.e("Tri", "vong lap: " + i);
            if(mList.get(i).get_id().equals(items.get_id())) {
                Log.e("Tri", mList.get(i).getName() + "co so luong: " + mList.get(i).getQuantity());
                mList.get(i).setQuantity(mList.get(i).getQuantity() + items.getQuantity());
            }
        }
    }

    public int ToltalPrice(List<Items> items){
        int toltal = 0;
        if(items == null || items.isEmpty()){
            return toltal;
        }
        for(Items i: items){
            toltal += i.getPrice()*i.getQuantity();
            Log.e("Tri", "toltal = " + toltal);
        }
        return toltal;
    }

    public void deleteItemsCart(Items items){
        this.mList.remove(items);
        cartView.DisplayListCartUdated(mList);
    }

    public void addFavoriteProduct(Items items){
        deleteItemsCart(items);
        cartView.DisplayListCartUdated(mList);
        Log.e("Tri", "đã thêm " + items.getName() + " vào yêu thích");
    }

    public void decrementQuantity(Items items){
        if(items.getQuantity() == 1)
            return;
        for(int i  = 0; i < this.mList.size(); i++){
            Log.e("Tri", "vong lap: " + i);
            if(mList.get(i).get_id().equals(items.get_id())) {
                Log.e("Tri", mList.get(i).getName() + "co so luong: " + mList.get(i).getQuantity());
                mList.get(i).setQuantity(mList.get(i).getQuantity() - 1);
            }
        }
        cartView.DisplaytotlalPrice(ToltalPrice(mList));
        cartView.DisplayListCartUdated(mList);
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

    @Override
    public void getListCartSuccess(List<Items> listItems) {
        mList.addAll(listItems);
        cartView.DisplayListCartSuccess(mList);
        cartView.DisplaytotlalPrice(ToltalPrice(listItems));
        cartView.DisplayNumProduct(listItems.size());
    }
}
