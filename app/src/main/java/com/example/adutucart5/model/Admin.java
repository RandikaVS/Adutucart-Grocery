package com.example.adutucart5.model;

public class Admin {

    public String id;
    public String name;
    public String email;
    public String mobile;
    public String password;
    public String imageUrl;

    public String status;



    public Admin(){}

    public Admin(String name, String email, String mobile, String password, String imageUrl) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.imageUrl = imageUrl;
        this.status = "0";
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
