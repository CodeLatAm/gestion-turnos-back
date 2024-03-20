package com.getion.turnos.controller;

import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.model.response.MessageResponse;
import com.getion.turnos.service.injectionDependency.PaymentService;
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
    public ResponseEntity<MessageResponse> createPayment(@Valid @RequestBody PaymentRequest request){
        paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse(HttpStatus.CREATED, "Pago creado"));
    }
}
