package com.example.shopproject.sqlite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopproject.mode.Account;

@Dao
public interface AccountDAO {

    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM Account")
    Account getAccount();

    @Query("DELETE FROM Account")
    void deleteAccount();
}
