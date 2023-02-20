package com.example.adutucart5.model;

import java.util.List;

public class AdminOrderItemList {

    private List<CustomerOrderList> customerOrderList;

    private String key;
    public AdminOrderItemList(){}

    public AdminOrderItemList(List<CustomerOrderList> customerOrderList){
        this.customerOrderList = customerOrderList;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public List<CustomerOrderList> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<CustomerOrderList> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }
}
