package com.example.parkease;

public class ModelBookedParking {
    String vehicle_number,vehicle_type,parking_name,slot,parking_address,phone,time,amount;

    public ModelBookedParking(String phone, String time, String amount, String vehicle_number, String vehicle_type, String parking_name, String slot, String parking_address) {
        this.phone = phone;
        this.time = time;
        this.amount = amount;
        this.vehicle_number = vehicle_number;
        this.vehicle_type = vehicle_type;
        this.parking_name = parking_name;
        this.slot = slot;
        this.parking_address = parking_address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getParking_name() {
        return parking_name;
    }

    public void setParking_name(String parking_name) {
        this.parking_name = parking_name;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getParking_address() {
        return parking_address;
    }

    public void setParking_address(String parking_address) {
        this.parking_address = parking_address;
    }
}
