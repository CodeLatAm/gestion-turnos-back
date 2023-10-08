package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.UserResponse;

import java.util.Optional;

public interface UserService {
    UserEntity findById(Long id);

    UserResponse findByIdUser(Long id);


}
