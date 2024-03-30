package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.Payment;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.response.PaymentResponse;
import com.getion.turnos.model.response.UserPaymentResponse;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentResponse mapToPaymentRequest(Payment order) {
        return PaymentResponse.builder()
                .id(order.getId())
                .total(order.getTotal())
                .dateCreated(order.getDateCreated())
                .paymentStatus(order.getPaymentStatus())
                .lastUpdate(order.getLastUpdate())
                .preferenceIdPaymentMPago(order.getPreferenceIdPaymentMPago())
                .orderReferenceExternal(order.getOrderReferenceExternal())

                .user(this.mapToUser(order.getUser()))
                .build();
    }

    private UserPaymentResponse mapToUser(UserEntity user) {
        return UserPaymentResponse.builder()
                .country(user.getCountry())
                .title(user.getTitle())
                .name(user.getName())
                .itsVip(user.isItsVip())
                .lastname(user.getLastname())
                .specialty(user.getSpecialty())
                .username(user.getUsername())
                .country(user.getCountry())
                .build();
    }
}
