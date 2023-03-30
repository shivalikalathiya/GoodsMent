package com.example.goodsment.Model;

public class Labour {

    String name ;
    String  mnumber;
    String address ;
    String  aadhar;

    public Labour(String name, String mnumber, String address, String aadhar) {
        this.name = name;
        this.mnumber = mnumber;
        this.address = address;
        this.aadhar = aadhar;
    }

    public Labour() {
    }

    public String getName() {
        return name;
    }

    public String getMnumber() {
        return mnumber;
    }

    public String getAddress() {
        return address;
    }

    public String getAadhar() {
        return aadhar;
    }
}
