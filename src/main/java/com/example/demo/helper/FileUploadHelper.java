package com.example.demo.helper;

import com.example.demo.dto.FileDeleteDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Component
@Slf4j
public class FileUploadHelper {

    protected final Log logger = LogFactory.getLog(FileUploadHelper.class);

    /**
     * To upload the file in your local directory
     * /**
     * To upload the file in your global location
     * String UPLOAD_DIR = new ClassPathResource("").getFile().getAbsolutePath();
     */

    public Boolean uploadFile(MultipartFile file) throws IOException {
        Boolean isUploaded = false;
        String UPLOAD_DIR = new ClassPathResource("").getFile().getAbsolutePath();
        logger.info("ClassPath :: " + UPLOAD_DIR);
        try {
            Files.copy(
                    file.getInputStream(),
                    Paths.get(UPLOAD_DIR + File.separator + file.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING
            );
            isUploaded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUploaded;
    }

    public Boolean deleteFile(FileDeleteDto filesDto) throws IOException {
        String UPLOAD_DIR = new ClassPathResource("").getFile().getAbsolutePath();
        Boolean isDeleted = false;
        List<String> listOfFilesNamesToDelete = filesDto.getListOfFileNames();

        for (String fileName : listOfFilesNamesToDelete) {
            logger.info("fileName :: " + fileName);
            Path path = java.nio.file.Paths.get(UPLOAD_DIR + File.separator + fileName);
            logger.info("Path :: " + path);
            isDeleted = Files.deleteIfExists(path);
        }
        return isDeleted;
    }
}
