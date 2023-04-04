package com.example.shopproject.mode;

import java.io.Serializable;

public class Size implements Serializable {
    private String name;
    private int countSize;

    public Size(String name, int countSize) {
        this.name = name;
        this.countSize = countSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountSize() {
        return countSize;
    }

    public void setCountSize(int countSize) {
        this.countSize = countSize;
    }
}
