package com.example.shopproject.sqlite.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shopproject.sqlite.DAO.AccountDAO;
import com.example.shopproject.sqlite.DAO.SearchHistoryDAO;
import com.example.shopproject.sqlite.Entity.Account;
import com.example.shopproject.sqlite.Entity.SearchHistory;

@Database(entities = {SearchHistory.class, Account.class}, version = 2)
public  abstract class ShopProjectDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "shop.db";
    private static ShopProjectDatabase instance;

    public static synchronized ShopProjectDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ShopProjectDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract SearchHistoryDAO searchHistoryDAO();

    public abstract AccountDAO accountDAO();

}
