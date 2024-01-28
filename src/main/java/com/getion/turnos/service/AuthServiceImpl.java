package com.getion.turnos.service;


import com.getion.turnos.Security.jwt.JwtUtil;
import com.getion.turnos.Security.util.CookieUtil;
import com.getion.turnos.enums.Role;
import com.getion.turnos.exception.UserNotFoundException;
import com.getion.turnos.mapper.UserMapper;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.RoleEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.LoginRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.LoginResponse;
import com.getion.turnos.model.response.RegisterResponse;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.AuthService;
import com.getion.turnos.service.injectionDependency.ProfileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;

    @Transactional
    @Override
    public RegisterResponse register(RegisterRequest request) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(request.getUsername());
        if(userEntity.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El Profesional ya esta registrado");

        }
        UserEntity user = userMapper.mapToAuthRequest(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(List.of(RoleEntity.builder()
                        .name(Role.PROFESSIONAL)
                .build()));
        ProfileEntity profileCreate = profileService.createProfile(request.getName(), request.getLastname(),request.getTitle(),
                request.getCountry(), request.getSpecialty(),user);
        user.setProfile(profileCreate);
        UserEntity userCreate = userRepository.save(user);

        RegisterResponse response = userMapper.mapToAuthResponse(userCreate);
        response.setToken(jwtUtil.generate(request.getUsername()));
        return response;
    }

    @Override
    public LoginResponse login(HttpServletResponse httpServletResponse, LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        Optional<UserEntity> userOptional = userRepository.findByUsername(authentication.getName());
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            String jwt = jwtUtil.generate(request.getUsername());
            return userMapper.mapToLoginResponse(user, jwt);
        } else {
            // Manejar el caso en que el usuario no está presente en la base de datos
            throw new UserNotFoundException("Usuario no encontrado");
        }
    }
}
