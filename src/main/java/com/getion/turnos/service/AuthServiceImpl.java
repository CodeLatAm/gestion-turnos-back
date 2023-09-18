package com.getion.turnos.service;

import com.getion.turnos.Security.filters.JwtTokenFilter;
import com.getion.turnos.Security.jwt.JwtUtil;
import com.getion.turnos.enums.Role;
import com.getion.turnos.mapper.UserMapper;
import com.getion.turnos.model.entity.RoleEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.LoginRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.LoginResponse;
import com.getion.turnos.model.response.RegisterResponse;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(RegisterRequest request) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if(userEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El Profesional ya esta registrado");
        }
        UserEntity user = userMapper.mapToAuthRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(RoleEntity.builder()
                        .name(Role.PROFESSIONAL)
                .build()));
        UserEntity userCreate = userRepository.save(user);
        RegisterResponse response = userMapper.mapToAuthResponse(userCreate);
        response.setToken(jwtUtil.generate(request.getUsername()));
        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        Optional<UserEntity> user = userRepository.findByUsername(authentication.getName());
        String jwt = jwtUtil.generate(request.getUsername());
        return userMapper.mapToLoginResponse(user.get(), jwt);
    }
}
