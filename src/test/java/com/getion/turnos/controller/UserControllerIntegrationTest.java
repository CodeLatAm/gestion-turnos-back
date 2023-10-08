package com.getion.turnos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.UserResponse;
import com.getion.turnos.service.AuthServiceImpl;
import com.getion.turnos.service.UserServiceImpl;
import com.getion.turnos.testutil.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
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

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

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

    private Optional<UserEntity> createUserForTest(){
        RegisterRequest request = TestUtils.creteUser();
        authService.register(request);
        return userService.findByUsername(request.getUsername());
    }

    @Test
    public void getUserById() throws Exception {
        Optional<UserEntity> user = createUserForTest();
        //ProfileRequest request = TestUtils.createProfileRequest();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        UserResponse response = objectMapper.readValue(content, UserResponse.class);

        assertThat(response.getName()).isEqualTo("Abel");
        assertThat(response.getTitle()).isEqualTo("DR");
        assertThat(response.getLastname()).isEqualTo("Acevedo");
        assertThat(response.getCountry()).isEqualTo("ARG");
    }







}
