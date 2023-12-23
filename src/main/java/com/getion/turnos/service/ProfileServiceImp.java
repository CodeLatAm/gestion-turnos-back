package com.getion.turnos.service;


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
        if(userEntity.getProfile() == null){
            ProfileEntity profileEntity = new ProfileEntity();
            profileEntity.setName(userEntity.getName());
            profileEntity.setLastname(userEntity.getLastname());
            profileEntity.setTitle(userEntity.getTitle());
            userEntity.setProfile(profileEntity);
            response.setName(profileEntity.getName());
            response.setLastname(profileEntity.getLastname());
            response.setTitle(profileEntity.getTitle());

        }else {
            ProfileEntity profile = userEntity.getProfile();
            response = profileMapper.mapToProfile(profile);

        }
        return response;
    }
}
