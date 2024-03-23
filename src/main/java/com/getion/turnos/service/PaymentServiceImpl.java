package com.getion.turnos.service;

import com.getion.turnos.enums.PaymentEnum;
import com.getion.turnos.mapper.PaymentMapper;
import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.model.response.PaymentResponse;
import com.getion.turnos.repository.PaymentRepository;
import com.getion.turnos.service.injectionDependency.MercadoPagoService;
import com.getion.turnos.service.injectionDependency.PaymentService;
import com.getion.turnos.service.injectionDependency.UserService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.getion.turnos.mapper.PaymentMapper;
import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.request.PaymentRequest;
import com.getion.turnos.repository.PaymentRepository;
import com.getion.turnos.service.injectionDependency.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final MercadoPagoService mercadoPagoService;
    private final PaymentMapper paymentMapper;

    @Transactional
    @Override
    public PaymentResponse createPayment(PaymentRequest request) throws MPException, MPApiException {
        UserEntity user = userService.findById(request.getUserId());
        Payment order = Payment.builder()
                .total(request.getTotal())
                .user(user)
                .paymentStatus(PaymentEnum.PENDIENTE)
                .dateCreated(LocalDate.now())
                .lastUpdate(LocalDate.now())
                .orderReferenceExternal(UUID.randomUUID().toString())
                .build();
        order.setPreferenceIdPaymentMPago(mercadoPagoService.createOrderPayment(order));
        paymentRepository.save(order);
        return paymentMapper.mapToPaymentRequest(order);
    }

}
