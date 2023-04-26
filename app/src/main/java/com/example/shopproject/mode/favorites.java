package com.example.shopproject.mode;

public class favorites {

    private String product;
    private String _id;

    public favorites(String product, String _id) {
        this.product = product;
        this._id = _id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
