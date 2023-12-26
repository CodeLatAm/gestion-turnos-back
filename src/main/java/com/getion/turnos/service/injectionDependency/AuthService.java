package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.LoginRequest;
import com.getion.turnos.model.request.RegisterRequest;
import com.getion.turnos.model.response.LoginResponse;
import com.getion.turnos.model.response.RegisterResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(HttpServletResponse httpServletResponse, LoginRequest request);
}
