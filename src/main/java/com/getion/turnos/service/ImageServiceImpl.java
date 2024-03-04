package com.getion.turnos.service;

import com.getion.turnos.model.entity.ImageEntity;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.repository.ImageRepository;
import com.getion.turnos.service.injectionDependency.ImageService;
import com.getion.turnos.service.injectionDependency.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProfileService profileService;

    @Transactional
    @Override
    public void uploadImage(Long userId, MultipartFile file) throws IOException {
        ProfileEntity profile = profileService.findById(userId);
        ImageEntity image = profile.getImage();
        image.setImageData(file.getBytes());
        image.setImageName(file.getOriginalFilename());
        image.setImageType(file.getContentType());
        image.setImageSizeBytes(file.getSize());
        profile.setImage(image);
        imageRepository.save(image);
    }
}
