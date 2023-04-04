package com.example.shopproject.mode;

import java.io.Serializable;
import java.util.List;

public class Color implements Serializable {
    private String name;
    private int countColor;
    private List<Size> size;
    private List<String> image;

    public Color(String name, int countColor, List<Size> size, List<String> image) {
        this.name = name;
        this.countColor = countColor;
        this.size = size;
        this.image = image;
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

    public List<Size> getSize() {
        return size;
    }

    public void setSize(List<Size> size) {
        this.size = size;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}
