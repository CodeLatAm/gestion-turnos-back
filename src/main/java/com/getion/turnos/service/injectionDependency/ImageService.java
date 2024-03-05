package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.response.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void uploadImage(Long userId, MultipartFile file) throws IOException;
    ImageResponse getImageByUserId(Long userId);
}
