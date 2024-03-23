package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.model.response.PaymentResponse;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request) throws MPException, MPApiException;
}
