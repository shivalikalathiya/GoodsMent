package com.example.goodsment.Model;

public class Charges {
    public String Charge;

    public String Type;

    public String imgUrl;

    public Charges(String charge, String type, String imgUrl) {
        Charge = charge;
        Type = type;
        this.imgUrl = imgUrl;
    }

    public Charges() {
    }
}
