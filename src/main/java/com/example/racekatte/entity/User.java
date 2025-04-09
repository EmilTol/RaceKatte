package com.example.racekatte.entity;

import java.util.List;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String postalCode;
    private String phoneNumber;

    private List<Cat> cats;

    public User() {
    }

    public User(int id, String email, String password, String firstName, String lastName, String postalCode, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Cat> getCats() {
        return cats;
    }
    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

}
