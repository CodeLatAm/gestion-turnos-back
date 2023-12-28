package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.request.ProfileRequest;
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
                .province(profile.getProvince())
                .country(profile.getCountry())
                .build();
    }

    public ProfileEntity mapToProfileUpdate(ProfileRequest request) {
        return ProfileEntity.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .title(request.getTitle())
                .city(request.getCity())
                .mat_nac(request.getMat_nac())
                .mat_prov(request.getMat_prov())
                .phone(request.getPhone())
                .domicile(request.getDomicile())
                .whatsapp(request.getWhatsapp())
                .specialty(request.getSpecialty())
                .country(request.getCountry())
                .presentation(request.getPresentation())
                .province(request.getProvince())
                .build();
    }

    public void updateProfileFromRequest(ProfileEntity existingProfile, ProfileRequest request) {
        existingProfile.setName(request.getName());
        existingProfile.setLastname(request.getLastname());
        existingProfile.setTitle(request.getTitle());
        existingProfile.setCountry(request.getCountry());
        existingProfile.setSpecialty(request.getSpecialty());
        existingProfile.setCity(request.getCity());
        existingProfile.setDomicile(request.getDomicile());
        existingProfile.setMat_nac(request.getMat_nac());
        existingProfile.setMat_prov(request.getMat_prov());
        existingProfile.setPhone(request.getPhone());
        existingProfile.setPresentation(request.getPresentation());
        existingProfile.setWhatsapp(request.getWhatsapp());
        existingProfile.setProvince(request.getProvince());
    }
}
