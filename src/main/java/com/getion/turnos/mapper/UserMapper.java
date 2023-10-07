package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.LoginResponse;
import com.getion.turnos.model.response.RegisterResponse;
import lombok.Builder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Builder
@Component
public class UserMapper {

    public UserEntity mapToAuthRequest(RegisterRequest request) {
        return UserEntity.builder()
                .name(request.getName())
                .country(request.getCountry())
                .username(request.getUsername())
                .title(request.getTitle())
                .lastname(request.getLastname())
                .build();
    }

    public RegisterResponse mapToAuthResponse(UserEntity userCreate) {
        return RegisterResponse.builder()
                .message("Registro exitoso")
                .build();
    }


    public LoginResponse mapToLoginResponse(UserEntity user, String jwt) {

        return LoginResponse.builder()
                .id(user.getId())
                .role(user.getRoles().stream().map(roleEntity -> roleEntity.getName()).collect(Collectors.toList()).toString())
                .lastname(user.getLastname())
                .name(user.getName())
                .token(jwt)
                .message("Logueo exitoso")
                .build();
    }

    public ProfileEntity mapToProfileRequest(ProfileRequest request) {
        return ProfileEntity.builder()
                .city(request.getCity())
                .phone(request.getPhone())
                .domicile(request.getDomicile())
                .mat_nac(request.getMat_nac())
                .mat_prov(request.getMat_prov())
                .province(request.getProvince())
                .presentation(request.getPresentation())
                .specialty(request.getSpecialty())
                .whatsapp(request.getWhatsapp())
                .city(request.getCity())
                .name(request.getName())
                .lastname(request.getLastname())
                .title(request.getTitle())
                .build();
    }
}
