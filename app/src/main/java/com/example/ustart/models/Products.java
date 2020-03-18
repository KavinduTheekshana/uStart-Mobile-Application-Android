package com.example.ustart.models;

public class Products {
    private String priductName,ProductPrice,productImage;

    public Products(String priductName, String productPrice, String productImage) {
        this.priductName = priductName;
        ProductPrice = productPrice;
        this.productImage = productImage;
    }

    public String getPriductName() {
        return priductName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}
