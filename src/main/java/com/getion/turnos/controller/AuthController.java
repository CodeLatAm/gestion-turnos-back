package com.getion.turnos.controller;

import com.getion.turnos.Security.util.CookieUtil;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.LoginRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.LoginResponse;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.model.response.RegisterResponse;
import com.getion.turnos.service.injectionDependency.AuthService;
import com.getion.turnos.service.injectionDependency.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    @Value("${jwt.accessTokenCookieName}")
    private String cookieName;
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request){
        RegisterResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(HttpServletResponse httpServletResponse, @Valid @RequestBody LoginRequest request){
            LoginResponse response = authService.login(httpServletResponse, request);
            return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("/details")
    public ResponseEntity<Object> getUserDetails(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        String userName = userDetails.getUsername();
        Optional<UserEntity> user= this.userService.getByUserName(userName);
        if (!user.isPresent())
            return new ResponseEntity<>(new MessageResponse(HttpStatus.NOT_FOUND,"Usuario no encontrado").getStatus());
        return new ResponseEntity<>(user.get(), HttpStatus.OK) ;
    }

    @GetMapping("/logout")
    public ResponseEntity<MessageResponse> logOut(HttpServletResponse httpServletResponse){
        CookieUtil.clear(httpServletResponse, cookieName);
        return new ResponseEntity<>(new MessageResponse(HttpStatus.OK, "session cerrada").getStatus()) ;
    }
}
