package com.getion.turnos.service;

import com.getion.turnos.exception.UserNotFoundException;
import com.getion.turnos.mapper.UserMapper;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.UserResponse;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            //throw new UserNotFoundException("No se encontró un usuario con el ID: " + id);
            throw new UserNotFoundException(String.format("No se encontró un usuario con el ID: %s ", id));
        }

        return user.get();
    }

    @Override
    public UserResponse findByIdUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new UserNotFoundException(String.format("No se encontró un usuario con el ID: %s ", id));
        }
        UserResponse response = userMapper.mapToUserResponse(user.get());
        return response;
    }

    public Optional<UserEntity> findByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFoundException(String.format("El usuario con email %s no está registrado", username));
        }
        return user;
    }
}
