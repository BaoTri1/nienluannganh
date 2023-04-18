package com.example.shopproject.presenter.Handle;

public interface CallbackPayment {
    void PaymentByCardSuccess(String message);
    void PaymentByCardFailue(String message);
}
