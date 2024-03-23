package com.getion.turnos.controller;

import com.getion.turnos.service.injectionDependency.MercadoPagoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    @PostMapping("/notify")
    public ResponseEntity<String> mercadoPagoNotificationPayment(@RequestBody Map<String, Object> values) {
       log.info("Entrando al controller mercadoPagoNotificationPayment()");
        if(mercadoPagoService.processNotificationWebhook(values)) {

            return ResponseEntity.ok("ACCEPTED");
        }
        else
            return new ResponseEntity<>(  "PAYMENT REQUIERED", HttpStatus.PAYMENT_REQUIRED);
    }
}
