package com.getion.turnos.controller;

import com.getion.turnos.model.response.VoucherResponse;
import com.getion.turnos.service.injectionDependency.VoucherService;
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

}
