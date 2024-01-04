package com.getion.turnos.controller;

import com.getion.turnos.model.response.CurrentUserResponse;
import com.getion.turnos.model.response.HealthCenterResponse;
import com.getion.turnos.model.response.UserResponse;
import com.getion.turnos.service.injectionDependency.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse response = userService.findByIdUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUserResponse> getCurrentUser(){
        CurrentUserResponse response = userService.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/centers/{id}")
    public ResponseEntity<List<HealthCenterResponse>> getAllCenterForUser(@PathVariable Long id){
        List<HealthCenterResponse> responses = userService.getAllCenterForUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(responses);

    }
}
