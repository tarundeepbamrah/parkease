package com.example.parkease;

import java.util.List;

public class GetBookedParkingResponse {String status;
    String message;
    List<ModelBookedParking> data;

    public GetBookedParkingResponse(String status, String message, List<ModelBookedParking> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ModelBookedParking> getData() {
        return data;
    }

    public void setData(List<ModelBookedParking> data) {
        this.data = data;
    }
}
