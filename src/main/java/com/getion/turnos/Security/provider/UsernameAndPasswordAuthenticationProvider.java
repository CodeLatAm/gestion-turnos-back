package com.getion.turnos.Security.provider;

import com.getion.turnos.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameAndPasswordAuthenticationProvider implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private static final String BAD_CREDENTIALS_MESSAGE = "El nombre de usuario o contraseña es incorrecto";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(authentication.getPrincipal()));

        if (userDetails != null && (passwordEncoder.matches(String.valueOf(authentication.getCredentials()), userDetails.getPassword()))) {
            return new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException(BAD_CREDENTIALS_MESSAGE);
        }
    }


    @Override
    public boolean supports(Class<?> authenticationType) {
        return UsernamePasswordAuthenticationToken.class.equals(authenticationType);
    }
}
