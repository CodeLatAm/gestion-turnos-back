package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String name;
    private String lastname;
    private String role;
    private String token;
    private String message;
    private Long profileId;
    private String userName;

    public LoginResponse(String message){
        this.message = message;
    }

}
