package com.getion.turnos.Security;

import com.getion.turnos.Security.filters.JwtTokenFilter;
import com.getion.turnos.Security.provider.UsernameAndPasswordAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsernameAndPasswordAuthenticationProvider usernameAndPasswordAuthenticationProvider;
    //private final JwtEntryPoint jwtEntryPoint;
    private final JwtTokenFilter jwtTokenFilter;

   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
       return authenticationConfiguration.getAuthenticationManager();
   }

    @Autowired
    void registerProvider(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(usernameAndPasswordAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(httpBasic -> httpBasic.disable());
        http.formLogin(formLogin -> formLogin.disable());
        http.csrf(csrf -> csrf.disable());
        http.authorizeRequests()
                .requestMatchers(HttpMethod.POST, "/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/test/accessUser").hasAnyAuthority("PROFESSIONAL")
                .requestMatchers(HttpMethod.GET, "/test/accessAdmin").hasAnyAuthority("ADMIN")
                .requestMatchers(HttpMethod.POST, "/profile/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/user/{id}").permitAll()
                .anyRequest().permitAll();
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000/*", "http://localhost:4200/*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
