package com.example.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.demo.constant.Message;
import com.example.demo.dto.AddStudentRequest;
import com.example.demo.pdf.StudentPDF;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.StudentResponse;
import com.example.demo.service.IStudentService;
import com.fasterxml.jackson.core.JsonProcessingException;

import com.itextpdf.text.DocumentException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api; // Importing Api for the class level annotation
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = Message.STUDENT_CONTROLLER) // Updated @Tag to @Api with tags
@RequestMapping("/api/student")
public class StudentController {

    private final IStudentService studentService;

    @Autowired
    public StudentController(IStudentService studentService) {
        this.studentService = studentService;
    }

    @ApiOperation(value = "Ping Api") // Changed @Operation to @ApiOperation
    @GetMapping("/ping")
    public String getMessage() {
        return Message.PING;
    }

    @ApiOperation(value = Message.ADD_STUDENT) // Changed @Operation to @ApiOperation
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Object>> addStudent(@Valid @RequestBody AddStudentRequest request) {
        BaseResponse<Object> response = BaseResponse.builder()
                .successMsg("Student Added")
                .data(studentService.addStudentDetails(request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = Message.GET_ALL_STUDENT) // Changed @Operation to @ApiOperation
    @GetMapping("/getAll")
    public ResponseEntity<BaseResponse<Object>> getAllStudent() throws JsonProcessingException {
        BaseResponse<Object> response = BaseResponse.builder()
                .successMsg("Student List Fetched")
                .data(studentService.getAllStudent())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = Message.GET_STUDENT) // Changed @Operation to @ApiOperation
    @GetMapping("/get/{id}")
    public ResponseEntity<BaseResponse<Object>> getStudent(@PathVariable Integer id) {
        BaseResponse<Object> response = BaseResponse.builder()
                .successMsg("Student Fetched")
                .data(studentService.getStudentById(id))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = Message.UPDATE_STUDENT) // Changed @Operation to @ApiOperation
    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Object>> updateDetails(@PathVariable Integer id, @Valid @RequestBody AddStudentRequest request) {
        BaseResponse<Object> response = BaseResponse.builder()
                .successMsg("Student Updated")
                .data(studentService.updateDetails(id, request))
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = Message.DELETE_STUDENT) // Changed @Operation to @ApiOperation
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<BaseResponse<Object>> deleteDetails(@PathVariable Integer id) {
        BaseResponse<Object> response = BaseResponse.builder()
                .successMsg("Student Deleted")
                .data(studentService.deleteDetails(id)).build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(value = Message.GET_ALL_STUDENT_PDF) // Changed @Operation to @ApiOperation
    @GetMapping("/getAll/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException, DocumentException {
        response.setContentType("student/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=studentsDetails" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<StudentResponse> studentList = studentService.getAllStudent();
        StudentPDF exporter = new StudentPDF(studentList);
        exporter.export((javax.servlet.http.HttpServletResponse) response);
    }
}
