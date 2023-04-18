package com.example.shopproject.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.shopproject.mode.Items;
import com.example.shopproject.mode.ShippingAddress;
import com.example.shopproject.mode.orderResponse;
import com.example.shopproject.presenter.Handle.CallbackPayment;
import com.example.shopproject.presenter.Handle.Payment;
import com.example.shopproject.orther_handle.Publics;
import com.example.shopproject.presenter.callbackMode.CallbackProductMode;
import com.example.shopproject.repository.ProductInterator;
import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.view.PayMentView;
import com.example.shopproject.view.UI.DetailOrderActicity;

import java.util.List;

public class PaymentPresenter implements CallbackPayment, CallbackProductMode {

    private Context mContext;
    private PayMentView payMentView;
    private ShippingAddress shippingAddress;
    private List<Items> mList;
    private int totalPrice;
    private int priceShippingMethod;
    private int toltalPayment;
    private String nameMethodPayment;
    private boolean islistCart;

    private Payment payment;
    private ProductInterator productInterator;

    public PaymentPresenter(Context mContext, PayMentView payMentView) {
        this.mContext = mContext;
        this.payMentView = payMentView;
        payment = new Payment(mContext, this);
        this.productInterator = new ProductInterator(mContext, this);
    }

    public void ReceiveShippingAddress(ShippingAddress shippingAddress){
        this.shippingAddress = shippingAddress;
        payMentView.DisplayShippingAddress(shippingAddress.getFullName(), shippingAddress.getSDT(), shippingAddress.getAddress());
    }

    public void ReceiveListItemOrder(boolean islistCart, List<Items> mList){
        this.mList = mList;
        this.islistCart = islistCart;
        payMentView.DisplayListItemOrder(mList);
        toltalPriceProduct(mList);
    }

    private void toltalPriceProduct(List<Items> mList){
        totalPrice = 0;
        for(int i = 0; i < mList.size(); i++){
            totalPrice += mList.get(i).getPrice() * mList.get(i).getQuantity();
        }
        payMentView.DisplayToltalPriceProduct(Publics.formatGia(totalPrice));
    }

    public void ToltalPayment(int priceShippingMethod){
        this.priceShippingMethod = priceShippingMethod;
        this.toltalPayment = this.totalPrice + this.priceShippingMethod;
        payMentView.DisplayToltalPayment(Publics.formatGia(this.toltalPayment));
    }

    public void makePayments(String methodPayment){
        if(methodPayment.equals(Publics.PAYMENT_NOMAL)){
            this.nameMethodPayment = Publics.PAYMENT_NOMAL;
            if(Publics.isNetWorkAvaliable(mContext)){
                productInterator.PostOrders(mList, shippingAddress, "Thanh toán khi nhận hàng",
                        this.totalPrice, priceShippingMethod, 0, toltalPayment);
            }else {
                payMentView.DisplayNoNetwork("Đã xảy ra lỗi. Xin vui lòng kiểm tra lại kết nối mạng.");
            }

        }else if(methodPayment.equals(Publics.PAYMENT_ONLINE)){
            this.nameMethodPayment = Publics.PAYMENT_ONLINE;
            ProgressDialog dialog = new ProgressDialog(mContext);
            dialog.setMessage("Đang xử lý thanh toán....");
            dialog.show();

            payment.createCustomer(toltalPayment);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    payment.paymentFlow();
                }
            }, 3000);

            Handler handlerLoad = new Handler();
            handlerLoad.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 5500);
        }
    }

    @Override
    public void PaymentByCardSuccess(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        if(Publics.isNetWorkAvaliable(mContext)){
            productInterator.PostOrders(mList, shippingAddress, "Stripe",
                    this.totalPrice, priceShippingMethod, 0, toltalPayment);
        }else {
            payMentView.DisplayNoNetwork("Đã xảy ra lỗi. Xin vui lòng kiểm tra lại kết nối mạng.");
        }
    }

    @Override
    public void PaymentByCardFailue(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ordersSuccess(orderResponse orderResponse) {
        Toast.makeText(mContext, orderResponse.getMessage(), Toast.LENGTH_SHORT).show();
        payMentView.OpenDetailOrder(this.nameMethodPayment ,orderResponse);
        if(islistCart){
            ShopProjectDatabase.getInstance(mContext).itemCartDAO().deleteAllItemCart();
        }
    }

    @Override
    public void getDataFailure(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
