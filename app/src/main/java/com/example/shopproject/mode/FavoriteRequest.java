package com.example.shopproject.mode;

public class FavoriteRequest {

    private String _id;
    private String item;

    public FavoriteRequest(String _id, String item) {
        this._id = _id;
        this.item = item;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
