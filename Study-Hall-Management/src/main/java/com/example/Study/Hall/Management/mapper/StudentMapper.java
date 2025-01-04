package com.example.Study.Hall.Management.mapper;

import com.example.Study.Hall.Management.dto.StudentDTO;
import com.example.Study.Hall.Management.model.Student;

public class StudentMapper {

    public static Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setName(dto.getName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setBloodGroup(dto.getBloodGroup());
        student.setExamPreparation(dto.getExamPreparation());
        student.setPhoneNumber(dto.getPhoneNumber());
        student.setSeatNumber(dto.getSeatNumber());
        return student;
    }

    public static StudentDTO toDTO(Student entity) {
        StudentDTO dto = new StudentDTO();
        dto.setName(entity.getName());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setBloodGroup(entity.getBloodGroup());
        dto.setExamPreparation(entity.getExamPreparation());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setId(entity.getId());
        dto.setSeatNumber(entity.getSeatNumber());
        return dto;
    }
}
