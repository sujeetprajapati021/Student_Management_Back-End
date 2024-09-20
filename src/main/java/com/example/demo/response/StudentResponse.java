package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {
	private String studentId;
	private String name;
	private String age;
	private String branch;
	private String email;
	private String mobileNumber;
	private String studentNo;

}
