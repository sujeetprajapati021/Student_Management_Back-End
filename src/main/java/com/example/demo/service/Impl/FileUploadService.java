package com.example.demo.service.Impl;


import com.example.demo.dto.FileDeleteDto;
import com.example.demo.enums.Status;
import com.example.demo.exception.BadRequestException;
import com.example.demo.helper.FileUploadHelper;
import com.example.demo.response.BaseResponse;
import com.example.demo.service.IFileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Service
public class FileUploadService implements IFileUploadService {

    private final FileUploadHelper fileUploadHelper;

    public FileUploadService(FileUploadHelper fileUploadHelper) {
        this.fileUploadHelper = fileUploadHelper;
    }

    @Override
    public BaseResponse<Object> uploadFile(MultipartFile file) {
        validateFile(file);
        BaseResponse<Object> response = null;
        try {
            Boolean isFileUploaded = fileUploadHelper.uploadFile(file);
            if (isFileUploaded) {
                // to prepare the URI
                String data = ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/image/")
                        .path(file.getOriginalFilename())
                        .toUriString();

                response = BaseResponse.builder()
                        .successMsg("File uploaded")
                        .data(data)
                        .build();
            }
        } catch (IOException ex) {
            response = BaseResponse.builder()
                    .status(Status.FAILURE)
                    .error("IO Exception")
                    .errorMsg("File could not be uploaded. Something went wrong !!")
                    .build();

            ex.fillInStackTrace();
        }
        return response;
    }

    @Override
    public BaseResponse<Object> deleteFile(FileDeleteDto filesDto) {
        BaseResponse<Object> response = null;
        try {
            Boolean isDeleted = fileUploadHelper.deleteFile(filesDto);
            if (isDeleted) {
                response = BaseResponse.builder()
                        .successMsg("File Deleted")
                        .data(true)
                        .build();
            } else {
                response = BaseResponse.builder()
                        .data(false)
                        .status(Status.FAILURE)
                        .error("NOT FOUND")
                        .errorMsg("File not found in the classpath.")
                        .build();
            }
        } catch (IOException ex) {
            response = BaseResponse.builder()
                    .data(false)
                    .status(Status.FAILURE)
                    .error("IO Exception")
                    .errorMsg("File couldn't be deleted. something went wrong !!")
                    .build();
            ex.fillInStackTrace();
        }
        return response;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty())
            throw new BadRequestException("File can't ne blank");

        log.info("File Format :: " + file.getContentType());

        if (!file.getContentType().equals("image/jpeg") && !file.getContentType().equals("image/png"))
            throw new BadRequestException(
                    "Format not supported, please upload file with jpg or png format only !!");
    }

}
