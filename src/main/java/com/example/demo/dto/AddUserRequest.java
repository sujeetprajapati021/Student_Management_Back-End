package com.example.demo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class AddUserRequest {

    @NotBlank(message = "please enter username")
    private String username;
    @NotBlank(message = "please enter password")
    private String password;
    @NotBlank(message = "please enter firstName")
    private String firstName;
    @NotBlank(message = "please enter lastName")
    private String lastName;
    @NotBlank(message = "please enter mobileNumber")
    private String mobileNumber;
    @NotBlank(message = "please enter emailId")
    private String emailId;
}
