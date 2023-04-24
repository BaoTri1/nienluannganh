package com.example.shopproject.view;

import com.example.shopproject.mode.User;

public interface ProfileView {
    void DisplayUserSuccess(User user);
    void DisplayError(String error);
}
