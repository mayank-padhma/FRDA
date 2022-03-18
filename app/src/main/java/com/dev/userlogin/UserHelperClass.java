package com.dev.userlogin;

public class UserHelperClass {
    String username, phoneNo, password, country, CC;


    public UserHelperClass(String username, String phoneNo, String country, String CC) {
        this.username = username;
        this.phoneNo = phoneNo;
        this.country = country;
        this.CC = CC;
    }

    public UserHelperClass(String username, String phoneNo, String password, String country, String CC) {
        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
        this.country = country;
        this.CC = CC;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }
}
