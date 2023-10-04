package com.getion.turnos.service;

import com.getion.turnos.mapper.UserMapper;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.repository.ProfileRepository;
import com.getion.turnos.service.injectionDependency.ProfileService;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImp implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public void save(Long id, ProfileRequest request) {
        UserEntity userEntity = userService.findById(id);
        ProfileEntity profile = userMapper.mapToProfileRequest(request);
        profile.setUser(userEntity);
        profileRepository.save(profile);
    }
}