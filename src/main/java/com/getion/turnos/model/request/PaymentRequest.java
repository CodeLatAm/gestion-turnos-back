package com.getion.turnos.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequest {

    @NotNull(message = "El userId es requerido")
    private Long userId;
    @NotNull(message = "El total es requerido")
    private BigDecimal total;
    private Long voucherId;



}
