package com.getion.turnos.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.service.AuthServiceImpl;
import com.getion.turnos.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;



import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProfileControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AuthServiceImpl authService;


    private UserEntity createUserForTest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setLastname("Doe");
        registerRequest.setTitle("Mr.");
        registerRequest.setUsername("testuser@example.com");
        registerRequest.setPassword("securePassword");
        registerRequest.setCountry("USA");

        // Registra el usuario utilizando el servicio de usuario
        authService.register(registerRequest);

        // Recupera el usuario recién creado
        return userService.findById(1L);
    }

    private ProfileRequest createProfileRequest() {
        return ProfileRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .city("BS AS")
                .phone("1234567890")
                .domicile("Garin")
                .presentation("DR ONCOLOGO")
                .mat_prov("12A12")
                .specialty("ONCOLOGO")
                .province("BS AS")
                .whatsapp("123313121")
                .mat_nac("12A12")
                .build();
    }

}
