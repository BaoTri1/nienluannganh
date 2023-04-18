package com.example.shopproject.mode;

import java.util.List;

public class Province {

    private String name;
    private int code;
    private String codename;
    private String division_type;
    private String phone_code;
    private List<District> districts;

    public Province(String name, int code, String codename, String division_type, String phone_code, List<District> districts) {
        this.name = name;
        this.code = code;
        this.codename = codename;
        this.division_type = division_type;
        this.phone_code = phone_code;
        this.districts = districts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getDivision_type() {
        return division_type;
    }

    public void setDivision_type(String division_type) {
        this.division_type = division_type;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }
}
