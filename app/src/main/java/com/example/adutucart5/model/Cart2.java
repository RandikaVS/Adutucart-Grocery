package com.example.adutucart5.model;

public class Cart2 {

    String id;
    String image;
    String title;
    String quantity;
    String unitPrice;
    String subTotal;
    String productId;

    public Cart2(){}

    public Cart2(String image,String title,String qty, String subTotal,String prodId,String unitPrice){
        this.image = image;
        this.title = title;
        this.quantity = qty;
        this.subTotal = subTotal;
        this.productId = prodId;
        this.unitPrice = unitPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getProductId() {
        return productId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getTitle() {
        return title;
    }
}
