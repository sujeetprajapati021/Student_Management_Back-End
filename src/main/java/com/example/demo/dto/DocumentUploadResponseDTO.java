package com.example.demo.dto;


import com.example.demo.enums.DocUploadStatus;
import lombok.Data;

@Data
public class DocumentUploadResponseDTO {

    private String key;
    private DocUploadStatus status;

    public DocumentUploadResponseDTO(String key, DocUploadStatus status) {
        super();
        this.key = key;
        this.status = status;
    }

}
