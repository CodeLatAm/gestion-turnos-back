package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Payment;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.util.Map;

public interface MercadoPagoService {
    String createOrderPayment(Payment order) throws MPException, MPApiException;

    boolean processNotificationWebhook(Map<String, Object> values);
}
