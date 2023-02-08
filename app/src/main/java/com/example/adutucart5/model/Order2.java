package com.example.adutucart5.model;

public class Order2 {


    String productId;
    String productName;
    String productImage;
    String qty;
    String unitPrice;
    String title;
    String total;


    public  Order2(){}

    public Order2(String productName, String productImage, String qty,String unitPrice
    ,String total){
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.title = title;
        this.total = total;

    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }


    public void setQty(String qty) {
        this.qty = qty;
    }


    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }


    public String getUnitPrice() {
        return unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }



    public String getProductImage() {
        return productImage;
    }

    public String getProductName() {
        return productName;
    }


    public String getQty() {
        return qty;
    }
}
