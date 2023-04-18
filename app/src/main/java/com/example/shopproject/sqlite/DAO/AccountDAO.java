package com.example.shopproject.sqlite.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.shopproject.sqlite.Entity.Account;

@Dao
public interface AccountDAO {

    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM Account")
    Account getAccount();

    @Query("DELETE FROM Account")
    void deleteAccount();

    @Query("SELECT login FROM Account where email = :email")
    boolean checkLogin(String email);

    @Query("SELECT email FROM Account")
    String getEmail();

    @Query("Update Account set login = :state where email = :email")
    void setStateFalseLogin(String email, boolean state);


}
