package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {
    
    @NotBlank(message = "student first name must not be blank")
    private String name;
    @NotBlank(message = "student age must not be blank")
    private String age;
    @NotBlank(message = "student branch must not be blank")
    private String branch;
    @NotBlank(message = "student email must not be blank")
    private String email;
    @NotBlank(message = "student mobile number must not be blank")
    private String mobileNumber;
}
