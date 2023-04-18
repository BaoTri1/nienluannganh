package com.example.shopproject.mode;

public class ShippingMethod {

    private String nameMethod;
    private int priceMethod;
    private String timeOrders;

    public ShippingMethod(String nameMethod, int priceMethod, String timeOrders) {
        this.nameMethod = nameMethod;
        this.priceMethod = priceMethod;
        this.timeOrders = timeOrders;
    }

    public String getNameMethod() {
        return nameMethod;
    }

    public void setNameMethod(String nameMethod) {
        this.nameMethod = nameMethod;
    }

    public int getPriceMethod() {
        return priceMethod;
    }

    public void setPriceMethod(int priceMethod) {
        this.priceMethod = priceMethod;
    }

    public String getTimeOrders() {
        return timeOrders;
    }

    public void setTimeOrders(String timeOrders) {
        this.timeOrders = timeOrders;
    }
}
