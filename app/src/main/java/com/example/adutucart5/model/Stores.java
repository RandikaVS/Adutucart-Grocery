package com.example.adutucart5.model;

public class Stores {


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Stores(String image) {
        this.image = image;
    }

    String image;

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    String store_name;
}
