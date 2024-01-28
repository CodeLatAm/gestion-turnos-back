package com.getion.turnos.service;

import com.getion.turnos.exception.UserNotAuthenticatedException;
import com.getion.turnos.exception.UserNotFoundException;
import com.getion.turnos.mapper.UserMapper;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.CurrentUserResponse;
import com.getion.turnos.model.response.HealthCenterResponse;
import com.getion.turnos.model.response.UserResponse;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @Override
    public UserEntity findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            log.info(String.format("No se encontró un usuario con el ID: %s ", id));
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

    @Override
    public Optional<UserEntity> getByUserName(String userName) {
        Optional<UserEntity> user = userRepository.findByUsername(userName);
        if(user.isEmpty()){
            throw new UserNotFoundException("Usuario no encontrado");
        }
        return user;
    }

    public Optional<UserEntity> findByUsername(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UserNotFoundException(String.format("El usuario con email %s no está registrado", username));
        }
        return user;
    }

    @Override
    public CurrentUserResponse getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!authentication.isAuthenticated()){
            throw new UserNotAuthenticatedException("Usuario no  autenticado");
        }
        Optional<UserEntity> user = userRepository.findByUsername(authentication.getName());
        if(user.isEmpty()){
            throw new UserNotFoundException(String.format("El usuario con email %s no está registrado", authentication.getName()));
        }
        CurrentUserResponse response = userMapper.mapToCurrentUser(user.get());
        return response;
    }

    @Override
    public List<HealthCenterResponse> getAllCenterForUser(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isEmpty()){
            log.error(String.format("El usuario con id: %s no esta registrado", id));
            throw new UserNotFoundException(String.format("El usuario con id: %s no esta registrado", id));
        }
        List<HealthCenterResponse> responses = userMapper.mapToList(user.get().getCenters());
        return responses;
    }
}
