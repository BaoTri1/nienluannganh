package com.example.shopproject.mode;

import java.io.Serializable;

public class ShippingAddress implements Serializable {

    private String fullName;
    private String address;
    private String city;
    private String postalCode;
    private String country;
    private String SDT;

    public ShippingAddress(String fullName, String address, String city, String postalCode, String country, String SDT) {
        this.fullName = fullName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
        this.SDT = SDT;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }
}
