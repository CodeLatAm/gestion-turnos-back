package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.response.ProfileResponse;
import org.springframework.stereotype.Component;

@Component

public class ProfileMapper {
    public ProfileResponse mapToProfile(ProfileEntity profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
                .name(profile.getName())
                .lastname(profile.getLastname())
                .city(profile.getCity())
                .phone(profile.getPhone())
                .title(profile.getTitle())
                .specialty(profile.getSpecialty())
                .whatsapp(profile.getWhatsapp())
                .domicile(profile.getDomicile())
                .presentation(profile.getPresentation())
                .mat_nac(profile.getMat_nac())
                .mat_prov(profile.getMat_prov())
                .build();
    }
}
