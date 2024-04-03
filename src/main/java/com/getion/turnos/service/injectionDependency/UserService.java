package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.CurrentUserResponse;
import com.getion.turnos.model.response.HealthCenterResponse;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.model.response.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserEntity findById(Long id);

    UserResponse findByIdUser(Long id);


    Optional<UserEntity> getByUserName(String userName);

    CurrentUserResponse getCurrentUser();

    List<HealthCenterResponse> getAllCenterForUser(Long id);

    Optional<UserEntity> findByUsername(String userEmail);

    MessageResponse verifyStatusUser(Long userId);

    List<UserEntity> findByItsVip(boolean b);
}
