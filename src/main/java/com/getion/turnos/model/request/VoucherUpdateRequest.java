package com.getion.turnos.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherUpdateRequest {
    @NotNull(message = "El userId es requerido")
    private Long userId;
    @NotNull(message = "El voucherId es requerido")
    private Long voucherId;
}
