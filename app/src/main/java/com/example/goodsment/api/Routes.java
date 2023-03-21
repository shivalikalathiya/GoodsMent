package com.example.goodsment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Routes extends Legs{

    ArrayList<Legs> legs = null;

    @SerializedName("overview_polyline")
    @Expose
     OverviewPolyline overviewPolyline;

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }


    public ArrayList<Legs> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Legs> legs) {
        this.legs = legs;
    }
}