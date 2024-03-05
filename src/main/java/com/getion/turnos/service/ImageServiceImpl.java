package com.getion.turnos.service;

import com.getion.turnos.mapper.ImageMapper;
import com.getion.turnos.model.entity.ImageEntity;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.ImageResponse;
import com.getion.turnos.repository.ImageRepository;
import com.getion.turnos.service.injectionDependency.ImageService;
import com.getion.turnos.service.injectionDependency.ProfileService;
import com.getion.turnos.service.injectionDependency.UserService;
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
    private final UserService userService;
    private final ImageMapper imageMapper;

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

    @Override
    public ImageResponse getImageByUserId(Long userId) {
        UserEntity user = userService.findById(userId);
        ImageResponse response = imageMapper.mapToImageEntity(user.getProfile().getImage());
        return response;
    }

}
