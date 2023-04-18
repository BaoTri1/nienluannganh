package com.example.shopproject.sqlite.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ItemCart")
public class itemCart {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String email;
    private String slug;
    private String name;
    private int quantity;
    private String image;
    private int price;
    private String _id;
    private int indexColor;
    private int indexSize;
    private String color;
    private String size;

    public itemCart(String email, String slug, String name, int quantity, String image,
                    int price, String _id, int indexColor, int indexSize, String color, String size) {
        this.email = email;
        this.slug = slug;
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
        this._id = _id;
        this.indexColor = indexColor;
        this.indexSize = indexSize;
        this.color = color;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
