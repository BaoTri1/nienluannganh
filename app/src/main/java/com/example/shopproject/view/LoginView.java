package com.example.shopproject.view;

import com.example.shopproject.mode.User;

public interface LoginView {
    void DisplayLoginSuccess(User user);
    void DisplayLoginFailue(String message);
    void DisplayMistakeOfEmail(String erorr);
    void DisplayMistakeOfPasswd(String erorr);

}
