package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.ImageEntity;
import com.getion.turnos.model.response.ImageResponse;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageResponse mapToImageEntity(ImageEntity image) {
        return ImageResponse.builder()
                .imageData(image.getImageData())
                .imageName(image.getImageName())
                .id(image.getId())
                .imageSizeBytes(image.getImageSizeBytes())
                .imageType(image.getImageType())
                .build();
    }
}
