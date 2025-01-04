package com.example.Study.Hall.Management.controller;

import com.example.Study.Hall.Management.Constants.CommonConstants;
import com.example.Study.Hall.Management.Constants.StudentConstants;
import com.example.Study.Hall.Management.dto.StudentDTO;
import com.example.Study.Hall.Management.exception.ResourceNotFoundException;
import com.example.Study.Hall.Management.mapper.StudentMapper;
import com.example.Study.Hall.Management.model.Student;
import com.example.Study.Hall.Management.service.StudentService;
import com.example.Study.Hall.Management.wrappers.ResponseWrapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseWrapper<?>> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        List<String> checkValidations = studentService.validateField(studentDTO);
        if (!checkValidations.isEmpty()) {
            ResponseWrapper<List<String>> errorResponse = new ResponseWrapper<>(false, checkValidations);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        Student savedStudent = studentService.createStudent(studentDTO);
        StudentDTO studentDTO1 = StudentMapper.toDTO(savedStudent);

        ResponseWrapper<StudentDTO> successResponse = new ResponseWrapper<>(true, studentDTO1);
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }


    @GetMapping("/all")
    public ResponseEntity<ResponseWrapper<?>> getAllStudents() {
        try {
            List<StudentDTO> students = studentService.getAllStudents();
            ResponseWrapper<List<StudentDTO>> successResponse = new ResponseWrapper<>(true, students);
            return ResponseEntity.ok(successResponse);
        } catch (Exception e) {
            ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false, CommonConstants.UNEXPECTED_ERROR);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(failureResponse);
        } // use global error handler
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> getStudentById(@PathVariable String id) {
//        try {
//            StudentDTO student = studentService.getStudentById(id);
//            ResponseWrapper<StudentDTO> successResponse = new ResponseWrapper<>(true, student);
//            return ResponseEntity.ok(successResponse);
//        } catch (ResourceNotFoundException e) {
//            ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false, StudentConstants.STUDENT_NOT_FOUND);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failureResponse);
//        }
        StudentDTO student = studentService.getStudentById(id);
        ResponseWrapper<StudentDTO> successResponse = new ResponseWrapper<>(true, student);
        return ResponseEntity.ok(successResponse);
    }

    @PutMapping("update")
    public ResponseEntity<ResponseWrapper<?>> updateStudent(@RequestBody StudentDTO studentDTO) {
        try {
            Student updatedStudent = studentService.updateStudent(studentDTO);
            ResponseWrapper<StudentDTO> successResponse = new ResponseWrapper<>(true, StudentMapper.toDTO(updatedStudent));
            return ResponseEntity.ok(successResponse);
        } catch (ResourceNotFoundException e) {
            ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false, CommonConstants.UNEXPECTED_ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failureResponse);
        } // use global error handler
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> deleteStudent(@PathVariable String id) {
        try {
            studentService.deleteStudent(id);
            ResponseWrapper<StudentDTO> successResponse = new ResponseWrapper<>(true);
            return ResponseEntity.ok().body(successResponse);
        } catch (ResourceNotFoundException e) {
            ResponseWrapper<String> failureResponse = new ResponseWrapper<>(false,CommonConstants.UNEXPECTED_ERROR);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(failureResponse);
        } // use global error handler
    }

    @GetMapping("/{studentId}/allocate-seat/{seatId}")
    public ResponseEntity<ResponseWrapper<?>> allocateSeat(@PathVariable String studentId,@PathVariable String seatId) {
        Student student = studentService.allotSeat(studentId,seatId);
        StudentDTO studentDTO = StudentMapper.toDTO(student);
        ResponseWrapper<StudentDTO> successResponse = new ResponseWrapper<>(true,studentDTO);
        return ResponseEntity.ok().body(successResponse);
    }

}
