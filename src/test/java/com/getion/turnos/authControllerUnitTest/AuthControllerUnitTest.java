package com.getion.turnos.authControllerUnitTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getion.turnos.controller.AuthController;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.RegisterResponse;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerUnitTest {

    private MockMvc mockMvc;
    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setLastname("Doe");
        registerRequest.setTitle("Mr.");
        registerRequest.setUsername("john.doe@example.com");
        registerRequest.setPassword("securePassword");
        registerRequest.setCountry("USA");

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setToken("someToken");
        registerResponse.setMessage("Registration successful");

        when(authService.register(any(RegisterRequest.class))).thenReturn(registerResponse);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isCreated());
    }
    @Test
    public void testRegisterUserAlreadyExists() throws Exception {
        RegisterRequest registerRequest = createRegisterRequest();
        // Simula un usuario existente configurando el repositorio de usuarios para devolver un resultado existente
        when(userRepository.findByUsername(registerRequest.getUsername())).thenReturn(Optional.of(new UserEntity()));
        // Simula una excepción cuando se llama a authService.register con el usuario existente
        when(authService.register(any(RegisterRequest.class))).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "El Profesional ya esta registrado"));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(registerRequest)))
                .andExpect(status().isNotFound()); // Puedes ajustar el estado HTTP según cómo manejes los errores en tu aplicación
    }

    private RegisterRequest createRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setLastname("Doe");
        registerRequest.setTitle("Mr.");
        registerRequest.setUsername("existingUser@example.com"); // El correo electrónico ya está registrado
        registerRequest.setPassword("securePassword");
        registerRequest.setCountry("USA");
    return registerRequest;

    }

    // Helper method to convert object to JSON string
    private String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
