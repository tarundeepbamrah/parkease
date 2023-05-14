package com.example.parkease;

import java.util.List;

public class GetAvailableParkingResponse {
    String status;
    String message;
    List<ModelAvailableParking> data;

    public GetAvailableParkingResponse(String status, String message, List<ModelAvailableParking> data) {
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

    public List<ModelAvailableParking> getData() {
        return data;
    }

    public void setData(List<ModelAvailableParking> data) {
        this.data = data;
    }
}
