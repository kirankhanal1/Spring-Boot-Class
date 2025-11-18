package com.cosmo.training.core.file.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
    String uploadFile(MultipartFile file);
}
