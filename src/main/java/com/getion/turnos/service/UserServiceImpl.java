package com.getion.turnos.service;

import com.getion.turnos.exception.UserNotFoundException;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException("No se encontró un usuario con el ID: " + id);
        }

        return user.get();
    }
}
