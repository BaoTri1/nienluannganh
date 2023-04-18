package com.example.shopproject.mode;

import java.io.Serializable;

public class orderResponse implements Serializable {

    private String message;
    private Orders order;

    public orderResponse(String message, Orders order) {
        this.message = message;
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}
