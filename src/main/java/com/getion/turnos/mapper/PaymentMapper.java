package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.request.PaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment mapToPaymentRequest(PaymentRequest request) {
        return Payment.builder()
                .description(request.getDescription())
                .paymentEnum(request.getPaymentEnum())
                .total(request.getTotal())
                .date(request.getDate())
                .build();
    }
}
