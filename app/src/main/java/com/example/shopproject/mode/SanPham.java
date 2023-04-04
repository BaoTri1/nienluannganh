package com.example.shopproject.mode;

public class SanPham {
    private int maSP;
    private String tenSP;
    private float danhgia;
    private int gia;
    private int slban;
    private int slconlai;
    private int giamgia;
    private String danhmuc;
    private boolean yeuthich;
    private int resourceId;

    public SanPham(int maSP, String tenSP, float danhgia, int gia, int slban, int giamgia, int resourceId, boolean yeuthich) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.danhgia = danhgia;
        this.gia = gia;
        this.slban = slban;
        this.giamgia = giamgia;
        this.resourceId = resourceId;
        this.yeuthich = yeuthich;
    }


    public SanPham(int maSP, String tenSP, float danhgia, int gia, int slban, int giamgia, int resourceId) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.danhgia = danhgia;
        this.gia = gia;
        this.slban = slban;
        this.giamgia = giamgia;
        this.resourceId = resourceId;
    }

    public float getDanhgia() {
        return danhgia;
    }

    public void setDanhgia(float danhgia) {
        this.danhgia = danhgia;
    }

    public int getSlban() {
        return slban;
    }

    public void setSlban(int slban) {
        this.slban = slban;
    }

    public int getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(int giamgia) {
        this.giamgia = giamgia;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setYeuthich(boolean yeuthich){
        this.yeuthich = yeuthich;
    }

    public boolean getYeuthich(){
        return yeuthich;
    }
}
