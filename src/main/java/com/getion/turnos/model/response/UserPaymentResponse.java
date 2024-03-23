package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPaymentResponse {
    private String name;
    private String lastname;
    private String title;
    private String username; //email
    private String country;
    private String specialty;
    private boolean itsVip;
    private ProfileResponse profile;
}
