package com.example.shopproject.sqlite.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SearchHistory")
public class SearchHistory {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String keywork;

    public SearchHistory(String keywork) {
        this.keywork = keywork;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeywork() {
        return keywork;
    }

    public void setKeywork(String keywork) {
        this.keywork = keywork;
    }
}
