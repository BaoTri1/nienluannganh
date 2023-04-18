package com.example.shopproject.mode;

import java.io.Serializable;
import java.util.List;

public class Orders implements Serializable {

    private List<Items> orderItems;
    private ShippingAddress shippingAddress;
    private String paymentMethod;
    private int itemsPrice;
    private int shippingPrice;
    private int taxPrice;
    private int totalPrice;
    private String user;
    //private User user;
    private boolean isPaid;
    private boolean isDelivered;
    private String _id;
    private String createdAt;

//    public Orders(ShippingAddress shippingAddress, String _id, List<Items> orderItems, String paymentMethod,
//                  int itemsPrice, int shippingPrice, int taxPrice, int totalPrice, User user, boolean isPaid, boolean isDelivered) {
//        this.shippingAddress = shippingAddress;
//        this._id = _id;
//        this.orderItems = orderItems;
//        this.paymentMethod = paymentMethod;
//        this.itemsPrice = itemsPrice;
//        this.shippingPrice = shippingPrice;
//        this.taxPrice = taxPrice;
//        this.totalPrice = totalPrice;
//        this.user = user;
//        this.isPaid = isPaid;
//        this.isDelivered = isDelivered;
//    }

    public Orders(ShippingAddress shippingAddress, String _id, List<Items> orderItems, String paymentMethod,
                  int itemsPrice, int shippingPrice, int taxPrice, int totalPrice, String user, boolean isPaid, boolean isDelivered, String createAt) {
        this.shippingAddress = shippingAddress;
        this._id = _id;
        this.orderItems = orderItems;
        this.paymentMethod = paymentMethod;
        this.itemsPrice = itemsPrice;
        this.shippingPrice = shippingPrice;
        this.taxPrice = taxPrice;
        this.totalPrice = totalPrice;
        this.user = user;
        this.isPaid = isPaid;
        this.isDelivered = isDelivered;
        this.createdAt = createAt;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<Items> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<Items> orderItems) {
        this.orderItems = orderItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getItemsPrice() {
        return itemsPrice;
    }

    public void setItemsPrice(int itemsPrice) {
        this.itemsPrice = itemsPrice;
    }

    public int getShippingPrice() {
        return shippingPrice;
    }

    public void setShippingPrice(int shippingPrice) {
        this.shippingPrice = shippingPrice;
    }

    public int getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(int taxPrice) {
        this.taxPrice = taxPrice;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCreateAt() {
        return createdAt;
    }

    public void setCreateAt(String createAt) {
        this.createdAt = createAt;
    }
}
