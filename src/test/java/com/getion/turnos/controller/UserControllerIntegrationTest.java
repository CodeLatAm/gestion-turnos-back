package com.getion.turnos.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.getion.turnos.model.response.MessageResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
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
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void verifyUserStatusActive () throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/1/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content = result.getResponse().getContentAsString();
        MessageResponse response = objectMapper.readValue(content, MessageResponse.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("ACTIVADO");

    }
    @Test
    public void verifyUserStatusDisable () throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/2/status"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        MessageResponse response = objectMapper.readValue(content, MessageResponse.class);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("DESACTIVADO");
    }

    @Test
    public void verifyStatusUser_UserNotFound() throws Exception {
        long nonExistentUserId = 9999L; // ID que no existe en la base de datos
        mockMvc.perform(MockMvcRequestBuilders.get("/users/" + nonExistentUserId + "/status"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }



}
