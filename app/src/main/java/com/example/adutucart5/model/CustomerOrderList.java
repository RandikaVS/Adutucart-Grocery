package com.example.adutucart5.model;

import com.example.adutucart5.adapter.CustomerOrderListAdapter;

import java.util.List;

public class CustomerOrderList {

    private String userKey;
    private String key;
    private String status;
    private String address;
    private String paymentType;
    private String subTotal;

    private List<CustomerOrderItemList> customerOrderItemList;

    public CustomerOrderList(){}
    public CustomerOrderList(List<CustomerOrderItemList> customerOrderItemList,String subTotal,String status,String address,String paymentType){
        this.status = status;
        this.address = address;
        this.paymentType = paymentType;
        this.customerOrderItemList = customerOrderItemList;
        this.subTotal = subTotal;
    }

    public void setParentKey(String userKey) {
        this.userKey = userKey;
    }

    public String getParentKey() {
        return userKey;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCustomerOrderItemList(List<CustomerOrderItemList> customerOrderItemList) {
        this.customerOrderItemList = customerOrderItemList;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getAddress() {
        return address;
    }

    public List<CustomerOrderItemList> getCustomerOrderItemList() {
        return customerOrderItemList;
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

}
