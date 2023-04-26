package com.example.shopproject.mode;

import java.util.List;

public class FavoriteResponse {

    private List<favorites> favorites;

    public FavoriteResponse(List<com.example.shopproject.mode.favorites> favorites) {
        this.favorites = favorites;
    }

    public List<com.example.shopproject.mode.favorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<com.example.shopproject.mode.favorites> favorites) {
        this.favorites = favorites;
    }
}
