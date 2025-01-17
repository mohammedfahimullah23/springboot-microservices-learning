package com.example.Study.Hall.Management.feignclientdto;

public class SeatDTO {
    private String id;
    private String status; // "AVAILABLE" or "RESERVED"

    public SeatDTO(String id, String status) {
        this.id = id;
        this.status = status;
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
