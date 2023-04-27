package com.example.shopproject.mode;


import java.io.Serializable;

/*
* "_id": "644a96e4c4fd749be7913966",
        "code": "DISCOUNT10",
        "discountPercentage": 10,
        "startDate": "2023-04-26T17:00:00.000Z",
        "endDate": "2023-04-28T17:00:00.000Z",
        "isActive": true,
        "__v": 0*/
public class Discount implements Serializable {

    private String _id;
    private String code;
    private int discountPercentage;
    private String startDate;
    private String endDate;
    private boolean isActive;

    public Discount(String _id, String code, int discountPercentage, String startDate, String endDate, boolean isActive) {
        this._id = _id;
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
