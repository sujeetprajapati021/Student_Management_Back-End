package com.example.demo.dto;

import lombok.Data;

import java.util.Date;


@Data
public class DocumentDataResponseDTO {
    private String signedUrl;
    private Date validUntil;

    public DocumentDataResponseDTO(String signedUrl, Date validUntil) {
        super();
        this.signedUrl = signedUrl;
        this.validUntil = validUntil;
    }

}
