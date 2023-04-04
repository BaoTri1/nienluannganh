package com.example.shopproject.view;

import com.example.shopproject.mode.User;

public interface RegisterView {
    void DisplayRegisterSuccess(User user);
    void DisplayRegisterFailue(String message);
    void DisplayMistakeOfName(String erorr);
    void DisplayMistakeOfEmail(String erorr);
    void DisplayMistakeOfPasswd(String erorr);
    void DisplayMistakeOfConfirmPasswd(String erorr);
}
