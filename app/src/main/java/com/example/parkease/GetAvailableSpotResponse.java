package com.example.parkease;

import java.util.List;

public class GetAvailableSpotResponse {
    String status;
    String message;
    List<ModelAvailableSpots> data;

    public GetAvailableSpotResponse(String status, String message, List<ModelAvailableSpots> data) {
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

    public List<ModelAvailableSpots> getData() {
        return data;
    }

    public void setData(List<ModelAvailableSpots> data) {
        this.data = data;
    }
}
