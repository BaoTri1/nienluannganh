package com.example.shopproject.mode;

import java.io.Serializable;
import java.util.List;

public class Color implements Serializable {
    private String name;
    private int countColor;
    private List<Size> sizes;
    private List<String> images;
    private String _id;

    public Color(String name, int countColor, List<Size> size, List<String> image, String _id) {
        this.name = name;
        this.countColor = countColor;
        this.sizes = size;
        this.images = image;
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountColor() {
        return countColor;
    }

    public void setCountColor(int countColor) {
        this.countColor = countColor;
    }

    public List<String> getImage() {
        return images;
    }

    public void setImage(List<String> image) {
        this.images = image;
    }

    public List<Size> getSizes() {
        return sizes;
    }

    public void setSizes(List<Size> sizes) {
        this.sizes = sizes;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
