package com.example.shopproject.mode;

public class reviewResponse {

    private String message;
    private Review review;
    private int numReviews;
    private int rating;

    public reviewResponse(String message, Review review, int numReviews, int rating) {
        this.message = message;
        this.review = review;
        this.numReviews = numReviews;
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
