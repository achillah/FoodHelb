package com.helb.foodhelb.Model;

public class User {
    private String id;
    private String name;
    private String lastname;
    private String mobile;

    public User() {
    }

    public User(String id, String name, String lastname, String mobile) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.mobile = mobile;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
