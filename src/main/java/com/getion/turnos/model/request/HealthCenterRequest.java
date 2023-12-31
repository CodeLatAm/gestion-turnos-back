package com.getion.turnos.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthCenterRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "La direccion es requerida")
    private String address;
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "El email es requerido")
    private String email;
    @NotBlank(message = "La especialidad es requerida")
    private String specialty;
}
