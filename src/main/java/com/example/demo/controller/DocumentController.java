package com.example.demo.controller;


import com.example.demo.response.BaseResponse;
import com.example.demo.service.Impl.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/document")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService docUploadService;

    @PutMapping("/upload")
    public BaseResponse<Object> uploadToS3(
            @RequestParam("fileName") String fileName,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        return BaseResponse.builder()
                .successMsg("Document Uploaded")
                .data(docUploadService.upload(fileName, file))
                .build();
    }

    @DeleteMapping
    public BaseResponse<Object> deleteFromS3(
            @RequestParam("fileName") String fileName) throws IOException {

        return BaseResponse.builder()
                .successMsg("Document Deleted")
                .data(docUploadService.delete(fileName))
                .build();
    }

    @GetMapping
    public BaseResponse<Object> fetchFromS3(
            @RequestParam("key") String documentKey) throws IOException {

        return BaseResponse.builder()
                .successMsg("Document Signed URL Fetched")
                .data(docUploadService.fetchDoc(documentKey))
                .build();
    }

}
