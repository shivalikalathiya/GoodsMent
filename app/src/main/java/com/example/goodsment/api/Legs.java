package com.example.goodsment.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Legs  {

    Distances distance;
    Durations duration;

    @SerializedName("start_location")
    @Expose
    private StartLocation startLocation;

    @SerializedName("end_location")
    @Expose
    private EndLocation endLocation;

    public Distances getDistance() {
        return distance;
    }

    public void setDistance(Distances distance) {
        this.distance = distance;
    }

    public Durations getDuration() {
        return duration;
    }

    public void setDuration(Durations duration) {
        this.duration = duration;
    }

    public EndLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(EndLocation endLocation) {
        this.endLocation = endLocation;
    }

    public StartLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(StartLocation startLocation) {
        this.startLocation = startLocation;
    }


    public class Distances{
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        String text;
        double value;

    }

    public class Durations{
        String text;
        double value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }
    }

}