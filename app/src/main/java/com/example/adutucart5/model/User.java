package com.example.adutucart5.model;

 
public class User {
    String id;
    String name;
    String email;
    String mobile;
    String password;

    String status;

    String idImage;


    public User() {
    }

    public User(String name, String email, String mobile, String password,String idImage) {

        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.idImage = idImage;
        this.status = "0";

    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
