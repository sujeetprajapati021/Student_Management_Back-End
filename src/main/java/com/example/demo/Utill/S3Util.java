package com.example.demo.Utill;

import com.amazonaws.AmazonClientException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

import com.example.demo.dto.DocumentDeleteResponseDTO;
import com.example.demo.dto.DocumentUploadResponseDTO;
import com.example.demo.enums.DocUploadStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Date;

@Component
@Slf4j
public class S3Util {

    @Value("${aws.s3.bucket_name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    public DocumentUploadResponseDTO uploadFile(String key, byte[] bytes, String fileName) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            s3Client.putObject(bucketName, key, byteArrayInputStream, metadata);
        } catch (AmazonClientException ex) {
            log.error("Error uploading document with bucketName: {} and fileName: {}", bucketName, fileName);
            return new DocumentUploadResponseDTO(null, DocUploadStatus.FAILURE);
        }
        return null;
    }

    public DocumentDeleteResponseDTO deleteFile(String key) {
        try {
            s3Client.deleteObject(bucketName, key);
        } catch (AmazonClientException ex) {
            log.error("Error deleting document with bucketName: {} and key: {}", bucketName, key);
            return new DocumentDeleteResponseDTO(DocUploadStatus.FAILURE);
        }
        return null;
    }

    public URL generateSignedUrlWithTime(String key, Date expiration) {
        try {
            return s3Client.generatePresignedUrl(bucketName, key, expiration);
        } catch(SdkClientException ex) {
            log.error("Error fetching doc signed URL with bucketName: {} and fileName: {}", bucketName, key);
        }
        return null;
    }}