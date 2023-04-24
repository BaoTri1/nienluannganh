package com.example.shopproject.sqlite.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Address")
public class Address {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String SDT;
    private String address;
    private String ward;
    private String district;
    private String province;
    private String email;

    public Address(String name, String SDT, String address, String ward, String district, String province, String email) {
        this.name = name;
        this.SDT = SDT;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
