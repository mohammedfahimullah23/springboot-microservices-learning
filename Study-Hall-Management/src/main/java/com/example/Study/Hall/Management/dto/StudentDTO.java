package com.example.Study.Hall.Management.dto;

import lombok.Data;

import java.time.LocalDate;

//@Data
public class StudentDTO {
    private String name;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private String examPreparation;
    private String phoneNumber;
    private String id;
    private String seatNumber;

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getExamPreparation() {
        return examPreparation;
    }

    public void setExamPreparation(String examPreparation) {
        this.examPreparation = examPreparation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
