package com.getion.turnos.controller;

import com.getion.turnos.model.request.VoucherUpdateRequest;
import com.getion.turnos.model.response.VoucherResponse;
import com.getion.turnos.service.injectionDependency.VoucherService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/vouchers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    @GetMapping("/{userId}")
    public ResponseEntity<List<VoucherResponse>> getAllVouchersByUserId(@PathVariable Long userId){
        List<VoucherResponse> responses = voucherService.getAllVouchersByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }
    @PutMapping("/update")
    public ResponseEntity<VoucherResponse> updatePayments(@Valid @RequestBody VoucherUpdateRequest request) throws MPException, MPApiException {

        VoucherResponse response = voucherService.updatePayment(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
