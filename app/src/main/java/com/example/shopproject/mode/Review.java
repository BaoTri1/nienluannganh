package com.example.shopproject.mode;

import java.io.Serializable;

public class Review implements Serializable {
    private String name;
    private String comment;
    private float rating;
    private String _id;

    public Review(String name, String comment, float rating, String _id) {
        this.name = name;
        this.comment = comment;
        this.rating = rating;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

