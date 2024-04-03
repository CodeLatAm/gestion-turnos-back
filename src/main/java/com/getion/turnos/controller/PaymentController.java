package com.getion.turnos.controller;

import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.model.request.VoucherRequest;
import com.getion.turnos.model.response.PaymentResponse;
import com.getion.turnos.service.injectionDependency.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) throws MPException, MPApiException {

        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PostMapping("/create/payment-list")
    public ResponseEntity<PaymentResponse> createPaymentList(@Valid @RequestBody VoucherRequest request) throws MPException, MPApiException {
        PaymentResponse response = paymentService.createPaymentVoucher(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
