package com.example.parkease;

public class ModelAvailableParking {
    String parking_name,parking_address,parking_capacity;

    public ModelAvailableParking(String parking_name, String parking_adress, String parking_capacity) {
        this.parking_name = parking_name;
        this.parking_address = parking_address;
        this.parking_capacity = parking_capacity;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getParking_address() {
        return parking_address;
    }

    public void setParking_address(String parking_address) {
        this.parking_address = parking_address;
    }

    public String getParking_capacity() {
        return parking_capacity;
    }

    public void setParking_capacity(String parking_capacity) {
        this.parking_capacity = parking_capacity;
    }
}
