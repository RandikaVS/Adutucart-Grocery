package com.example.adutucart5.model;

import java.util.List;

public class Product2 {

    String id;
    String title;
    String description;
    String price;
    String discount;
    String image;

    String qty;

    String productStore;

    public Product2() {
    }

    public Product2(String title, String description,String qty, String price, String discount, String image) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.image = image;
        this.qty = qty;
    }

    public void setProductStore(String productStore) {
        this.productStore = productStore;
    }

    public String getProductStore() {
        return productStore;
    }



    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getQty() {
        return qty;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
