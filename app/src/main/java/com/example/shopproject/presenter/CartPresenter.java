package com.example.shopproject.presenter;

import android.content.Context;
import android.util.Log;

import com.example.shopproject.mode.Items;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
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
    private int quantityUpdate;
    private Items itemsTmp;
    private boolean check;

    public CartPresenter(Context mContext, CartView cartView) {
        this.mContext = mContext;
        this.cartView = cartView;
        productInterator = new ProductInterator(mContext, this);
        mList = new ArrayList<>();
        check = false;
    }

    public void getListCart(){
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.getListCart();
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
            if(Objects.equals(mList.get(i).get_id(), items.get_id())) {
                if (mList.get(i).getIndexColor() == items.getIndexColor()) {
                    if (mList.get(i).getIndexSize() == items.getIndexSize()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void updatequantity(Items items){
        this.itemsTmp = items;
        check = true;
        for(int i  = 0; i < this.mList.size(); i++){
            Log.e("Tri", "vong lap: " + i);
            if(mList.get(i).get_id().equals(items.get_id())) {
                if(mList.get(i).getIndexColor() == items.getIndexColor()){
                    if(mList.get(i).getIndexSize() == items.getIndexSize()){
                        this.quantityUpdate = mList.get(i).getQuantity() + items.getQuantity();
                        if(items.getIndexColor() == -1){
                            productInterator.getCountInStock(items.getSlug());
                        }else {
                            if(items.getIndexSize() == -1){
                                productInterator.getCountColor(items.getSlug(), items.getIndexColor());
                            }else {
                                productInterator.getCountSize(items.getSlug(), items.getIndexColor(), items.getIndexSize());
                            }
                        }
                    }
                }
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

    public void deleteAllItems(){
        this.mList.clear();
        ShopProjectDatabase.getInstance(mContext).itemCartDAO().deleteAllItemCart();
        setIconNumItem();
        cartView.DisplaytotlalPrice(ToltalPrice(this.mList));
        cartView.DisplayListCartUdated(mList);
    }

    public void deleteItemsCart(Items items){
        this.mList.remove(items);
        String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
        ShopProjectDatabase.getInstance(mContext).itemCartDAO().deleteItemCart(items.getSlug(), email, items.getSize());
        setIconNumItem();
        cartView.DisplaytotlalPrice(ToltalPrice(this.mList));
        cartView.DisplayListCartUdated(mList);
        cartView.DisplayNumProduct(this.mList.size());
    }

    public void addFavoriteProduct(Items items){
        deleteItemsCart(items);
        cartView.DisplaytotlalPrice(ToltalPrice(this.mList));
        cartView.DisplayListCartUdated(this.mList);
        cartView.DisplayNumProduct(this.mList.size());
        Log.e("Tri", "đã thêm " + items.getName() + " vào yêu thích");
    }

    public void decrementQuantity(Items items){
        if(items.getQuantity() == 1)
            return;
        for(int i  = 0; i < this.mList.size(); i++){
            Log.e("Tri", "vong lap: " + i);
            if(mList.get(i).get_id().equals(items.get_id())) {
                if(mList.get(i).getIndexColor() == items.getIndexColor()){
                    if(mList.get(i).getIndexSize() == items.getIndexSize()){
                        Log.e("Tri", mList.get(i).getName() + "co so luong: " + mList.get(i).getQuantity());
                        mList.get(i).setQuantity(mList.get(i).getQuantity() - 1);
                        String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
                        ShopProjectDatabase.getInstance(mContext).itemCartDAO().updateQuantityItemCart(email,mList.get(i).getSlug(), mList.get(i).getSize(), mList.get(i).getQuantity());
                    }
                }
            }
        }
        cartView.DisplaytotlalPrice(ToltalPrice(mList));
        cartView.DisplayListCartUdated(mList);
    }

    public void incrementQuantity(Items items){
        this.numberIncreased = items.getQuantity() + 1;
        this.itemsTmp = items;
        if(items.getIndexColor() == -1){
            productInterator.getCountInStock(items.getSlug());
        }else {
            if(items.getIndexSize() == -1){
                productInterator.getCountColor(items.getSlug(), items.getIndexColor());
            }else {
                productInterator.getCountSize(items.getSlug(), items.getIndexColor(), items.getIndexSize());
            }
        }
    }

    public void CheckoutCart(){
        cartView.CheckoutCart(this.mList);
    }

    public void setIconNumItem(){
        String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
        int num = ShopProjectDatabase.getInstance(mContext).itemCartDAO().getNumItemCart(email);
        Log.e("Tri", String.valueOf(num));
        cartView.DisplayIconNumItem(num);
//        if(num != 0){
//            cartView.DisplayIconNumItem(num);
//        }
    }

    @Override
    public void getListCartSuccess(List<Items> listItems) {
        mList.addAll(listItems);
        cartView.DisplayListCartSuccess(mList);
        cartView.DisplaytotlalPrice(ToltalPrice(listItems));
        cartView.DisplayNumProduct(listItems.size());
    }

    @Override
    public void getNumProductAvailable(int number) {
        if(!check){
            if(numberIncreased > number){
                cartView.DisplayQuantityError("Sản phẩm đã hết hàng");
            }else {
                for(int i  = 0; i < this.mList.size(); i++){
                    Log.e("Tri", "vong lap: " + i);
                    if(mList.get(i).get_id().equals(this.itemsTmp.get_id())) {
                        if(mList.get(i).getIndexColor() == this.itemsTmp.getIndexColor()){
                            if(mList.get(i).getIndexSize() == this.itemsTmp.getIndexSize()){
                                Log.e("Tri", mList.get(i).getName() + "co so luong: " + mList.get(i).getQuantity());
                                mList.get(i).setQuantity(numberIncreased);
                                String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
                                ShopProjectDatabase.getInstance(mContext).itemCartDAO().updateQuantityItemCart(email,mList.get(i).getSlug(), mList.get(i).getSize(), numberIncreased);
                            }
                        }
                    }
                }
            }
        }
        else {
            if(quantityUpdate > number){
                quantityUpdate = number;
            }
            for(int i  = 0; i < this.mList.size(); i++){
                Log.e("Tri", "vong lap: " + i);
                if(mList.get(i).get_id().equals(this.itemsTmp.get_id())) {
                    if(mList.get(i).getIndexColor() == this.itemsTmp.getIndexColor()){
                        if(mList.get(i).getIndexSize() == this.itemsTmp.getIndexSize()){
                            Log.e("Tri", mList.get(i).getName() + "co so luong: " + mList.get(i).getQuantity());
                            mList.get(i).setQuantity(quantityUpdate);
                            String email = ShopProjectDatabase.getInstance(mContext).accountDAO().getEmail();
                            ShopProjectDatabase.getInstance(mContext).itemCartDAO().updateQuantityItemCart(email,mList.get(i).getSlug(), mList.get(i).getSize(), quantityUpdate);
                        }
                    }
                }
            }
        }
        cartView.DisplaytotlalPrice(ToltalPrice(mList));
        cartView.DisplayListCartUdated(mList);
        check = false;
    }
}
