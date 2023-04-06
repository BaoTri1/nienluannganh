package com.example.shopproject.sqlite.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopproject.sqlite.Entity.Account;

@Dao
public interface AccountDAO {

    @Insert
    void insertAccount(Account account);

    @Query("SELECT * FROM Account")
    Account getAccount();

    @Query("DELETE FROM Account")
    void deleteAccount();
}
