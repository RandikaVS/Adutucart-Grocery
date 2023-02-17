package com.example.adutucart5.model;

public class CustomerOrderItemList {

    private String productImage;
    private String productName;
    private String qty;
    private String unitPrice;
    private String total;

    public CustomerOrderItemList(){}

    public CustomerOrderItemList(String productImage,String productName,String qty,String total , String unitPrice){

        this.productName = productName;
        this.productImage = productImage;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotal() {
        return total;
    }

    public String getQty() {
        return qty;
    }

    public String getProductName() {
        return productName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getProductImage() {
        return productImage;
    }
}

