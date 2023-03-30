package com.example.goodsment.Model;

public class VehicleModel {

    public  VehicleModel(){}

    private String Type;
    private int Charge;
    private  String img_url;



    public VehicleModel(String type, int charge, String img_url) {
        this.Type = type;
        this.Charge = charge;
        this.img_url = img_url;
    }
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getCharge() {
        return Charge;
    }

    public void setCharge(int charge) {
        Charge = charge;
    }

    public  String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
