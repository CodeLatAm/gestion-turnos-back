package com.getion.turnos.service;

import com.getion.turnos.enums.Role;
import com.getion.turnos.model.entity.RoleEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private static final String LOGIN_EXCEPTION_MESSAGE = "No hay ninguna cuenta asociada con la dirección de correo electrónico.";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            UserEntity authUser = user.get();
            return new User(authUser.getUsername(),
                    authUser.getPassword(),
                    authUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).toList());
        } else {
            throw new UsernameNotFoundException(LOGIN_EXCEPTION_MESSAGE);
        }
    }
}
