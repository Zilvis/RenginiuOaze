package dev.zilvis.renginiuoaze.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    String saveImage(MultipartFile file) throws IOException;
}
