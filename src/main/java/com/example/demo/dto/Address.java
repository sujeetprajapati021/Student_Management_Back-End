package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {


    @NotBlank(message = "address must not be blank")
    private String address1;

    private String address2;

    @NotBlank(message = "pincode must not be blank")
    @Pattern(regexp = "(^[0-9]{6}$)")
    private String pincode;

    @NotBlank(message = "city must not be blank")
    @Pattern(regexp = "([a-zA-Z\\s+']{1,80}\\s*)+")
    private String city;

    @NotBlank(message = "state must not be blank")
    @Pattern(regexp = "([a-zA-Z\\s+']{1,80}\\s*)+")
    private String state;
}
