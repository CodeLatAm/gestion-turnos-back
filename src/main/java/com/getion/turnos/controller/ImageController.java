package com.getion.turnos.controller;

import com.getion.turnos.model.entity.ImageEntity;
import com.getion.turnos.model.response.ImageResponse;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ImageController {

    private final ImageService imageService;
    @PutMapping("/upload/{userId}")
    public ResponseEntity<MessageResponse> uploadImage(@PathVariable Long userId,
                                                       @RequestParam MultipartFile file) throws IOException {
        imageService.uploadImage(userId, file);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse(HttpStatus.OK, "Imagen creada con exito"));
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ImageResponse> getImageByUserId(@PathVariable Long userId){
         ImageResponse response = imageService.getImageByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
