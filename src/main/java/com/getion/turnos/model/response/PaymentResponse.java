package com.getion.turnos.model.response;

import com.getion.turnos.enums.PaymentEnum;
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
public class PaymentResponse {

    private Long id;
    private PaymentEnum paymentStatus;
    private BigDecimal total;
    private LocalDate dateCreated;
    private LocalDate lastUpdate;
    private String orderReferenceExternal;
    private String preferenceIdPaymentMPago;
    //private String initPoint;
    private UserPaymentResponse user;
}
