package com.example.shopproject.sqlite;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.shopproject.mode.SearchHistory;

import java.util.List;

@Dao
public interface SearchHistoryDAO {

    @Insert
    void insertSearchHistory(SearchHistory searchHistory);

    @Query("SELECT * FROM SearchHistory ORDER BY id")
    List<SearchHistory> getListSearchHistory();

    @Query("DELETE FROM SearchHistory")
    void deleteAllSearchHistory();

    @Query("SELECT * FROM SearchHistory WHERE keywork = :keywork")
    List<SearchHistory> checkSearchHistory(String keywork);

    @Query("SELECT * FROM SearchHistory ORDER BY id LIMIT 5")
    List<SearchHistory> getSmallListSearchHistory();

}


