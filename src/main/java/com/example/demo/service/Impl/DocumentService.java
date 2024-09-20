package com.example.demo.service.Impl;


import com.example.demo.Utill.S3Util;
import com.example.demo.dto.DocumentDataResponseDTO;
import com.example.demo.dto.DocumentDeleteResponseDTO;
import com.example.demo.dto.DocumentUploadResponseDTO;
import com.example.demo.enums.DocUploadStatus;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class DocumentService {

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    @Value("${aws.s3.document-url.expiryMin}")
    private Long documentUrlExpiry;

    @Autowired
    private S3Util s3Util;

    private final Integer additionMilliseconds = 60000;

    public DocumentUploadResponseDTO upload(String fileName, MultipartFile file) throws IOException {
        validateFile(file, fileName);
        s3Util.uploadFile(createS3key(fileName), file.getBytes(), fileName);
        return new DocumentUploadResponseDTO(fileName, DocUploadStatus.SUCCESS);
    }

    public DocumentDeleteResponseDTO delete(String key) {
        s3Util.deleteFile(key);
        return new DocumentDeleteResponseDTO(DocUploadStatus.SUCCESS);
    }

    public DocumentDataResponseDTO fetchDoc(String documentKey) {
        Long expiryTimeMillis = Instant.now().toEpochMilli() + (additionMilliseconds * documentUrlExpiry);
        Date expiry = new Date();
        expiry.setTime(expiryTimeMillis);
        URL url = s3Util.generateSignedUrlWithTime(documentKey, expiry);
        return new DocumentDataResponseDTO(url.toString(), expiry);
    }

    private Boolean isPdfFile(String contentType, String fileName) {
        return (Objects.equals(contentType, "application/pdf")
                || fileName.endsWith(".pdf")
                || fileName.endsWith(".PDF"));
    }

    private Boolean isPasswordProtected(MultipartFile file) throws IOException {
        try {
            PDDocument.load(file.getInputStream());
        } catch (InvalidPasswordException ex) {
            return true;
        }
        return false;
    }

    private String createS3key(String fileName) {
        return (UUID.randomUUID() + fileName);
    }

    private void validateFile(MultipartFile file, String fileName) throws IOException {
        if (file.getSize() == 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "file size should not be zero");

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename()).toLowerCase();

        if(!(extension.equals("jpg") || extension.equals("jpeg")
                || extension.equals("png") || extension.equals("pdf")))
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Please upload the file with JPG, JPEG, PDF and PNG extension"
        );

        if (isPdfFile(file.getContentType(), fileName)) {
            if(isPasswordProtected(file))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is password protected.");
        }
    }

}
