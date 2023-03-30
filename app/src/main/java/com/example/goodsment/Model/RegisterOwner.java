package com.example.goodsment.Model;

public class RegisterOwner {
    private String name,email,contact,city,password;

    public RegisterOwner(){

    }
    public RegisterOwner(String name, String email, String contact, String city , String password) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.city = city;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
