package com.example.demo.service;

import com.example.demo.dto.FileDeleteDto;
import com.example.demo.response.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileUploadService {
    BaseResponse<Object> uploadFile(MultipartFile file);

    BaseResponse<Object> deleteFile(FileDeleteDto request);
}
