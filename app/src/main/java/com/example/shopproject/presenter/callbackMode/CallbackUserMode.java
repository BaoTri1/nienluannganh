package com.example.shopproject.presenter.callbackMode;

import com.example.shopproject.mode.User;

public interface CallbackUserMode {
    default void LoginSuccess(User user) {}
    default void LoginFailure(String message) {}
    default void CallbackUserFailure(String message){}

    default void RegisterSuccess(User user) {}
    default void RegisterFailure(String message) {}

    default void GetUserSuccess(User user) {}
    default void GetUserFailure(String message) {}

}
