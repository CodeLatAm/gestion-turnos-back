package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.ProfileRequest;

public interface ProfileService {
    void save(Long id, ProfileRequest request);
}
