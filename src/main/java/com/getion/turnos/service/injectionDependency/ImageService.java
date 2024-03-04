package com.getion.turnos.service.injectionDependency;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void uploadImage(Long userId, MultipartFile file) throws IOException;
}
