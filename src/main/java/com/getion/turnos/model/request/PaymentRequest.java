package com.getion.turnos.model.request;

import com.getion.turnos.enums.PaymentEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "La fecha de la orden de pago es requerida")
    private LocalDate date;
    @NotNull(message = "El estado de la orden es requerido")
    private PaymentEnum paymentEnum;
    @NotBlank(message = "La descripción es requerida")
    private String description;
    @NotNull(message = "El total es requerido")
    private BigDecimal total;
}
