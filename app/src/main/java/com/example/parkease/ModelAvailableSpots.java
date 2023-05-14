package com.example.parkease;

public class ModelAvailableSpots {
    String parking_name,available_spot;

    public ModelAvailableSpots(String parking_name, String available_spot) {
        this.parking_name = parking_name;
        this.available_spot = available_spot;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getAvailable_spot() {
        return available_spot;
    }

    public void setAvailable_spot(String available_spot) {
        this.available_spot = available_spot;
    }
}
