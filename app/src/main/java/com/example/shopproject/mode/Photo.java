package com.example.shopproject.mode;

public class Photo {
    //private int resourceId;
    private String resourceId;

    public Photo(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }
}
