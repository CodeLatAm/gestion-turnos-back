package com.getion.turnos.controller;

import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.model.response.ProfileResponse;
import com.getion.turnos.service.injectionDependency.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/{id}")
    public ResponseEntity<MessageResponse> create(@PathVariable Long id, @Valid @RequestBody ProfileRequest request){
        profileService.save(id, request);
        String message = "Perfil creado";
        return ResponseEntity.ok(new MessageResponse(HttpStatus.CREATED, message));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId ){
        ProfileResponse response = profileService.getProfile(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
