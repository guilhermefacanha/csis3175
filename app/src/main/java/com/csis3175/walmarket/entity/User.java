package com.csis3175.walmarket.entity;

public class User {
    private Integer userId;
    private Integer applyFriendlyDisc = 0;
    private String fName;
    private String lName;
    private String email;
    private String address;
    private String password;


    //gets and sets
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getApplyFriendlyDisc() {
        return applyFriendlyDisc;
    }

    public void setApplyFriendlyDisc(Integer applyFriendlyDisc) {
        this.applyFriendlyDisc = applyFriendlyDisc;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
