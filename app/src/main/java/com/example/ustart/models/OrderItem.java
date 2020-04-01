package com.example.ustart.models;

public class OrderItem {
    String productName,qty,price,image,userImage;

    public OrderItem(){

    }

    public OrderItem(String productName, String qty, String price, String image, String userImage) {
        this.productName = productName;
        this.qty = qty;
        this.price = price;
        this.image = image;
        this.userImage = userImage;
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
