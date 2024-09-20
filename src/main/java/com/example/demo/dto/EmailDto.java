package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDto {

    private String to;
    private String[] toArray;
    private String from;
    private String subject;
    private String body;
    private String[] cc;
}
