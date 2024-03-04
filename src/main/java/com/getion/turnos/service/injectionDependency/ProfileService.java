package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.response.ProfileResponse;

public interface ProfileService {

    void save(Long id, ProfileRequest request);

    ProfileResponse getProfile(Long userId);

    ProfileEntity createProfile(String name, String lastname, String title, String country, String specialty, UserEntity user);

    void update(Long id, Long userId, ProfileRequest request);

    ProfileEntity findById(Long userId);
}
