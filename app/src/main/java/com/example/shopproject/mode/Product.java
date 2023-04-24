package com.example.shopproject.mode;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {

    private String _id;
    private String name;
    private String slug;
    private String category;
    private String image;
    private int price;
    private int countInStock;
    private float rating;
    private int numReviews;
    private String description;
    private List<Review> reviews;
    private List<Color> colors;

    private int idImg;

    public Product() {
    }

    public Product(String _id, String name, String slug, String category,
                   String image, int price, int countInStock, float rating,
                   int numReviews, String description, List<Review> reviews,
                   List<Color> color, boolean favorite) {
        this._id = _id;
        this.name = name;
        this.slug = slug;
        this.category = category;
        this.image = image;
        this.price = price;
        this.countInStock = countInStock;
        this.rating = rating;
        this.numReviews = numReviews;
        this.description = description;
        this.reviews = reviews;
        this.colors = color;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCountInStock() {
        return countInStock;
    }

    public void setCountInStock(int countInStock) {
        this.countInStock = countInStock;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdImg() {
        return idImg;
    }

    public void setIdImg(int idImg) {
        this.idImg = idImg;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Color> getColor() {
        return colors;
    }

    public void setColor(List<Color> color) {
        this.colors = color;
    }
}
