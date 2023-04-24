package com.example.shopproject.mode;

public class reviewRequest {

    private int rating;
    private String comment;
    private String name;

    public reviewRequest(int rating, String comment, String name) {
        this.rating = rating;
        this.comment = comment;
        this.name = name;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
