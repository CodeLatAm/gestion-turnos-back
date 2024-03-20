package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.PaymentRequest;

public interface PaymentService {
    void createPayment(PaymentRequest request);
}
