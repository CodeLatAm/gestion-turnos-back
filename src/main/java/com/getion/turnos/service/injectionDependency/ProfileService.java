package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.response.ProfileResponse;

public interface ProfileService {
    void save(Long id, ProfileRequest request);

    ProfileResponse getProfile(Long userId);
}
