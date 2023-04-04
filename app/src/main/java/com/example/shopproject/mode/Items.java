package com.example.shopproject.mode;

import java.io.Serializable;


//{
//        "slug": "ao002",
//        "name": "Ao Thun 2",
//        "quantity": 1,
//        "image": "/images/anh2.webp",
//        "price": 420000,
//        "product": "63f5fb6bab4cc2760e06aa95",
//        "_id": "63f5fb6bab4cc2760e06aa95"
//        }

public class Items implements Serializable {

    private String slug;
    private String name;
    private int quantity;
    private String image;
    private int price;
    private String _id;
    private int indexColor;
    private int indexSize;

    public Items() {}

    public Items(String slug, String name, int quantity, String image, int price, String _id, int indexColor, int indexSize) {
        this.slug = slug;
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
        this._id = _id;
        this.indexColor = indexColor;
        this.indexSize = indexSize;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getIndexColor() {
        return indexColor;
    }

    public void setIndexColor(int indexColor) {
        this.indexColor = indexColor;
    }

    public int getIndexSize() {
        return indexSize;
    }

    public void setIndexSize(int indexSize) {
        this.indexSize = indexSize;
    }
}
