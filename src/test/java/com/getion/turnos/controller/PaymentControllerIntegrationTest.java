package com.getion.turnos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Activa el perfil de pruebas
public class PaymentControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void verifyUserStatus() throws Exception {
        // Simulamos una solicitud HTTP GET al endpoint /users/{userId}/status
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("ACTIVO"));
    }

}
