package com.example.shopproject.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.shopproject.mode.Account;
import com.example.shopproject.mode.SearchHistory;

@Database(entities = {SearchHistory.class, Account.class}, version = 1)
public  abstract class ShopProjectDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "shop.db";
    private static ShopProjectDatabase instance;

    public static synchronized ShopProjectDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ShopProjectDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract SearchHistoryDAO searchHistoryDAO();

    public abstract AccountDAO accountDAO();

}
