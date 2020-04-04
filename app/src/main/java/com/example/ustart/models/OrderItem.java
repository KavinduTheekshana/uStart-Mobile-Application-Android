package com.example.ustart.models;

public class OrderItem {
    String id,productName,qty,price,image,userImage;

    public OrderItem(){

    }

    public OrderItem(String id, String productName, String qty, String price, String image, String userImage) {
        this.id = id;
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.image = image;
        this.userImage = userImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
