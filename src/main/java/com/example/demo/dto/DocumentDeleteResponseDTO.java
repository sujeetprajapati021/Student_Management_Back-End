package com.example.demo.dto;

import com.example.demo.enums.DocUploadStatus;

public class DocumentDeleteResponseDTO {

    private DocUploadStatus status;

    public DocumentDeleteResponseDTO(DocUploadStatus status) {
        super();
        this.status = status;
    }
}
