package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.UserEntity;

public interface UserService {
    UserEntity findById(Long id);
}
