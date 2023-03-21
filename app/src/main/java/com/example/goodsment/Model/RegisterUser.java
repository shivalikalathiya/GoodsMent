package com.example.goodsment.Model;

public class RegisterUser {
    private String name,email,contact,city,password;

    public RegisterUser() {
    }

    public RegisterUser(String name, String email,  String contact, String city,String password) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.city = city;
        this.password = password;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
