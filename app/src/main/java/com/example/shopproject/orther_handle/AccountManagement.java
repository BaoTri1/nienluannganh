package com.example.shopproject.orther_handle;

import android.content.Context;

import com.example.shopproject.sqlite.Database.ShopProjectDatabase;
import com.example.shopproject.sqlite.Entity.Account;


public class AccountManagement {

    public static Account account;

    public static void saveAccount(Context context, String email, String password, boolean loginState){
        ShopProjectDatabase.getInstance(context).accountDAO().insertAccount(new Account(email, password, loginState));
    }

    public static boolean checkStateLogin(Context context){
        account = getAccount(context);
        if(account == null)
            return false;
        return ShopProjectDatabase.getInstance(context).accountDAO().checkLogin(account.getEmail());
    }

    public static void deleteAccount(Context context){
        ShopProjectDatabase.getInstance(context).accountDAO().deleteAccount();
    }

    public static Account getAccount(Context context){
        return ShopProjectDatabase.getInstance(context).accountDAO().getAccount();
    }

    public static void setFalseLoginState(Context context, String email, boolean state){
        ShopProjectDatabase.getInstance(context).accountDAO().setStateFalseLogin(email, state);
    }

}
