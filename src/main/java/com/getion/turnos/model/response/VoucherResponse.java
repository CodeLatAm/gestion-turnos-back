package com.getion.turnos.model.response;

import com.getion.turnos.model.entity.UserEntity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoucherResponse {

    private Long id;
    private Long idTransaccion;
    private OffsetDateTime creationDateTime;
    private OffsetDateTime approvalDateTime;
    private String description;
    private String statusDetail;
    private String currencyId;
    private int installments;// Cuotas
    private BigDecimal transactionAmount;
    private String status;
    private String paymentTypeId;
    private UserPaymentResponse user;
}
