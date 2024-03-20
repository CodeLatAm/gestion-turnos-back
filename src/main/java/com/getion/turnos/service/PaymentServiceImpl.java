package com.getion.turnos.service;

import com.getion.turnos.mapper.PaymentMapper;
import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.repository.PaymentRepository;
import com.getion.turnos.service.injectionDependency.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public void createPayment(PaymentRequest request) {
        Payment payment = paymentMapper.mapToPaymentRequest(request);
        paymentRepository.save(payment);
    }
}
