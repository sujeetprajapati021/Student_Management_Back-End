package com.example.demo.service;

import com.example.demo.dto.AddStudentRequest;
import com.example.demo.response.StudentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;


import java.util.List;

public interface IStudentService {

	Boolean addStudentDetails(AddStudentRequest dto);

	List<StudentResponse>  getAllStudent() throws JsonProcessingException;

	StudentResponse getStudentById(Integer id);

	Boolean updateDetails(Integer id, AddStudentRequest dto);

	Boolean deleteDetails(Integer id);

}
