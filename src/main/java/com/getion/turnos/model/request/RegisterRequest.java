package com.getion.turnos.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El nombre es requerido")
    private String name;
    @NotBlank(message = "El apellido es requerido")
    private String lastname;
    @NotBlank(message = "El titulo es requerido")
    private String title;
    @NotBlank(message = "El email es requerido")
    @Email(message = "El mail debe ser valido")
    private String username; //email
    @NotBlank(message = "El password es requerido")
    @Size(min = 8, max = 25, message = "El password debe tener entre 8 y 25 carcateres")
    private String password;
    @NotBlank(message = "El pais es requerido")
    private String country;
    @NotBlank(message = "La especialidad es requerida")
    private String specialty;
}
