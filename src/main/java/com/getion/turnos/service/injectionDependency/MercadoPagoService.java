package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.entity.Voucher;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.util.Map;

public interface MercadoPagoService {
    String createOrderPayment(Payment order) throws MPException, MPApiException;

    boolean processNotificationWebhook(Map<String, Object> values);

    String updatePayment(Voucher voucherUpdate) throws MPException, MPApiException;

    public boolean processNotificationWebhookVoucher(Map<String, Object> data);
}
