package com.example.food_service.dto;


public class SeatDTO {
    private String id;
    private String seatName;
    private String status; // "AVAILABLE" or "RESERVED"

    public SeatDTO(String id, String status,String seatName) {
        this.id = id;
        this.status = status;
        this.seatName = seatName;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
