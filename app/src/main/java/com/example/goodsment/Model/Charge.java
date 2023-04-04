package com.example.goodsment.Model;

public class Charge {

    private String Charge;
    private String imgUrl;
    private String Type;
    public Charge(){}

    public Charge(String charge, String imgUrl,String Type) {
        this.Charge = charge;
        this.imgUrl = imgUrl;
        this.Type=Type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCharge() {
        return Charge;
    }

    public void setCharge(String charge) {
        Charge = charge;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
