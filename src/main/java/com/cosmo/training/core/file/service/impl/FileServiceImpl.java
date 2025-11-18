package com.cosmo.training.core.file.service.impl;

import com.cosmo.training.core.file.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);
    private final String uploadDirectory = "uploads/";

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            File folder = new File(uploadDirectory);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path path = Paths.get(uploadDirectory + fileName);

            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return fileName;

        } catch (Exception e) {
            log.error("File upload failed", e);
            throw new RuntimeException("File upload failed");
        }
    }
}
