package com.example.adutucart5.model;

import android.graphics.drawable.Drawable;

public class Stores {

    int image;
    String store_name;


    public Stores(int image,String name) {
        this.store_name = name;
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }


}
