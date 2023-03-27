package com.example.goodsment.Model;

public class VehicleModel {

    private String vehicleType;
    private String charge;

    public VehicleModel(String vehicleType, String charge) {
        this.vehicleType = vehicleType;
        this.charge = charge;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }
}
