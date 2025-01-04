package com.example.Study.Hall.Management.service;

import com.example.Study.Hall.Management.Constants.StudentConstants;
import com.example.Study.Hall.Management.dto.StudentDTO;
import com.example.Study.Hall.Management.exception.ResourceNotFoundException;
import com.example.Study.Hall.Management.feignclient.SeatServiceClient;
import com.example.Study.Hall.Management.mapper.StudentMapper;
import com.example.Study.Hall.Management.model.Student;
import com.example.Study.Hall.Management.repository.StudentRepository;
import com.example.Study.Hall.Management.utils.PhoneNumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final SeatServiceClient seatServiceClient;

    @Autowired
    public StudentService(StudentRepository studentRepository, SeatServiceClient seatServiceClient) {
        this.studentRepository = studentRepository;
        this.seatServiceClient = seatServiceClient;
    }

    public Student createStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.toEntity(studentDTO);
        return studentRepository.save(student);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(StudentMapper::toDTO).toList();
    }

    public StudentDTO getStudentById(String studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with ID " + studentId + " not found"));
        return StudentMapper.toDTO(student);
    }


    public void deleteStudent(String id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Student with ID " + id + " not found");
        }
    }

    public Student updateStudent(StudentDTO studentDTO) {
        Student existingStudent = studentRepository.findById(studentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + studentDTO.getId() + " not found"));

        existingStudent.setName(studentDTO.getName());
        existingStudent.setDateOfBirth(studentDTO.getDateOfBirth());
        existingStudent.setBloodGroup(studentDTO.getBloodGroup());
        existingStudent.setExamPreparation(studentDTO.getExamPreparation());
        existingStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        existingStudent.setSeatNumber(studentDTO.getSeatNumber());
        return studentRepository.save(existingStudent);
    }

    public Student allotSeat(String studentId, String seatId) {
        Student existingStudent = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + studentId + " not found"));
        // Check if the seat is not occupied before using openfeign or rest template
        // try both
        System.out.println("BYE");
       var isSeatReserved = this.seatServiceClient.checkReservationStatus(seatId);
       System.out.println("Hi");
       if(!isSeatReserved.getBody().isSuccess()) {
           existingStudent.setSeatNumber(seatId);
           Student savedStudent = studentRepository.save(existingStudent);
           if(savedStudent.getSeatNumber() != null){
               this.seatServiceClient.reserveSeat(seatId);
               return savedStudent;
           }
       }
        throw new ResourceNotFoundException("Seat with ID " + seatId + " is already reserved.");

    }

    public List<String> validateField(StudentDTO studentDTO) {
        List<String> validationIssues = new ArrayList<>();

        if (studentDTO.getName() == null) {
            validationIssues.add(StudentConstants.NAME_REQUIRED);
        }
        if (studentDTO.getDateOfBirth() == null) {
            validationIssues.add(StudentConstants.DOB_REQUIRED);
        }
        if (studentDTO.getBloodGroup() == null) {
            validationIssues.add(StudentConstants.BLOOD_GROUP_REQUIRED);
        }

        if (studentDTO.getPhoneNumber() == null) {
            validationIssues.add(StudentConstants.PHONE_NUMBER_REQUIRED);
        }

        if(!PhoneNumberValidator.validatePhoneNumber(studentDTO.getPhoneNumber())){
            validationIssues.add(StudentConstants.VALID_PHONE_NUMBER);
        }

        return validationIssues;
    }
}
