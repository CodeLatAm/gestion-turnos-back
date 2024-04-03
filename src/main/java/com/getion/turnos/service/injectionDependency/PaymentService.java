package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.model.request.VoucherRequest;
import com.getion.turnos.model.response.PaymentResponse;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest request) throws MPException, MPApiException;

<<<<<<< HEAD
    PaymentResponse createPaymentVoucher(VoucherRequest request) throws MPException, MPApiException;
=======
>>>>>>> d73a4930e6b32cc82c4c062879d5fa15834e7d40
}
