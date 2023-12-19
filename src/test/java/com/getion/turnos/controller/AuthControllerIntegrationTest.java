package com.getion.turnos.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.getion.turnos.model.request.LoginRequest;
import com.getion.turnos.model.request.RegisterRequest;

import com.getion.turnos.model.response.LoginResponse;
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
@ActiveProfiles("test") // Activa el perfil de pruebas
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegister() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName("John");
        registerRequest.setLastname("Doe");
        registerRequest.setTitle("Mr.");
        registerRequest.setUsername("testuser@example.com");
        registerRequest.setPassword("securePassword");
        registerRequest.setCountry("USA");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        // Verifica que la respuesta contenga el token y el mensaje
        String content = result.getResponse().getContentAsString();
        assertThat(content).contains("token");
        assertThat(content).contains("Registro exitoso");

    }

    @Test
    public void testRegisterFailure() throws Exception {
        RegisterRequest request = this.createRegisterRequest();
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        // Intentar registrar al mismo usuario nuevamente (esto debería generar un fallo)
        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect( result -> {
                    String content = result.getResponse().getContentAsString();
                    assertThat(content).contains("El Profesional ya esta registrado");
                });
    }

    private RegisterRequest createRegisterRequest() {
        return RegisterRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .country("ARG")
                .title("DR")
                .username("abel@gmail.com")
                .password("12345678")
                .build();
    }

    @Test
    public void correctLogin() throws Exception {
        RegisterRequest request = this.createRegisterRequestLogin();

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        LoginRequest loginRequest = this.createLoginRequest();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        // Deserializar la respuesta JSON en un objeto LoginResponse
        String content = result.getResponse().getContentAsString();
        LoginResponse loginResponse = objectMapper.readValue(content, LoginResponse.class);

        // Verificar los atributos del objeto LoginResponse

        assertThat(loginResponse.getId()).isEqualTo(1L);
        assertThat(loginResponse.getName()).isEqualTo("Abel"); // Reemplaza con el valor esperado
        assertThat(loginResponse.getLastname()).isEqualTo("Acevedo"); // Reemplaza con el valor esperado
        assertThat(loginResponse.getRole()).isEqualTo("[PROFESSIONAL]"); // Reemplaza con el valor esperado
        assertThat(loginResponse.getToken()).isNotNull();
        assertThat(loginResponse.getMessage()).isEqualTo("Logueo exitoso"); // Reemplaza con el valor esperado

    }

    private RegisterRequest createRegisterRequestLogin() {
        return RegisterRequest.builder()
                .name("Abel")
                .lastname("Acevedo")
                .country("ARG")
                .title("DR")
                .username("abel2@gmail.com")
                .password("12345678")
                .build();
    }

    private LoginRequest createLoginRequest() {
        return LoginRequest.builder()
                .username("abel2@gmail.com")
                .password("12345678")
                .build();

    }


}
