package com.example.shopproject.mode;

import java.io.Serializable;

public class ShippingAddress implements Serializable {

    private String fullName;
    private String address;
    private String city;
    private String district;
    private String ward;
    private String phone;

    public ShippingAddress(String fullName, String address, String city, String district, String ward, String phone) {
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.district = district;
        this.ward = ward;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
