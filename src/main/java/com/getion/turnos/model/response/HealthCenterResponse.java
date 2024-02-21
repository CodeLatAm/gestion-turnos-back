package com.getion.turnos.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthCenterResponse {

    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String specialty;
    private Integer totalPatients;

}
