package com.example.ustart.models;

public class Products {
    private String priductName,ProductPrice,productImage;

    public Products(String priductName, String productPrice) {
        this.priductName = priductName;
        ProductPrice = productPrice;

    }

    public String getPriductName() {
        return priductName;
    }

    public String getProductPrice() {
        return ProductPrice;
    }

}
