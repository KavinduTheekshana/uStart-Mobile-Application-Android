package com.example.ustart.models;

public class CartItem {

    String id,title,price,qty,total,image;

    public CartItem(){

    }

    public CartItem(String id, String title, String price, String qty, String total, String image) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.qty = qty;
        this.total = total;
        this.image = image;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
