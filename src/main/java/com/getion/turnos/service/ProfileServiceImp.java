package com.getion.turnos.service;


import com.getion.turnos.exception.ProfileNotFountException;
import com.getion.turnos.mapper.ProfileMapper;
import com.getion.turnos.mapper.UserMapper;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.response.ProfileResponse;
import com.getion.turnos.repository.ProfileRepository;
import com.getion.turnos.service.injectionDependency.ProfileService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final ProfileMapper profileMapper;


    @Transactional
    @Override
    public void save(Long id, ProfileRequest request) {
        UserEntity userEntity = userService.findById(id);
        if(profileRepository.existsByUser_Id(id)){
            // Lanzar una excepción indicando que el perfil ya existe
            throw new DataIntegrityViolationException("Profile already exists for user with id: " + id);
        }
        ProfileEntity profile = userMapper.mapToProfileRequest(request);
        profile.setUser(userEntity);
        profileRepository.save(profile);
    }

    @Override
    public ProfileResponse getProfile(Long userId) {
        ProfileResponse response = new ProfileResponse();
        UserEntity userEntity = userService.findById(userId);
            ProfileEntity profile = userEntity.getProfile();
            response = profileMapper.mapToProfile(profile);

        return response;
    }

    @Override
    public ProfileEntity createProfile(String name, String lastname, String title, String country, String specialty,
                                       UserEntity user) {
        ProfileEntity profile = new ProfileEntity();
        profile.setName(name);
        profile.setLastname(lastname);
        profile.setTitle(title);
        profile.setCountry(country);
        profile.setSpecialty(specialty);
        profile.setUser(user);
        profileRepository.save(profile);
        return profile;
    }

    @Transactional
    @Override
    public void update(Long id, Long userId, ProfileRequest request) {
        UserEntity user = userService.findById(userId);
        Optional<ProfileEntity> profile = profileRepository.findById(id);
        if(profile.isEmpty() || profile == null){
            throw new ProfileNotFountException(String.format("El perfil con id: %s no esta presente", id));
        }
        ProfileEntity existingProfile = profile.get();
        profileMapper.updateProfileFromRequest(existingProfile, request);
        // Suponiendo que hay una relación bidireccional entre UserEntity y ProfileEntity
        user.setProfile(existingProfile);
        profileRepository.save(existingProfile);
    }
}
