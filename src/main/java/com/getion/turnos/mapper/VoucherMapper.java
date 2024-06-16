package com.getion.turnos.mapper;

import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.model.response.UserPaymentResponse;
import com.getion.turnos.model.response.VoucherResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VoucherMapper {
    public List<VoucherResponse> mapToVouchers(List<Voucher> vouchers) {
        return vouchers.stream().map(this::mapToVoucherResponse)
                .collect(Collectors.toList());
    }

    public VoucherResponse mapToVoucherResponse(Voucher voucher) {
        return VoucherResponse.builder()
                .id(voucher.getId())
                .approvalDateTime(voucher.getApprovalDateTime())
                .creationDateTime(voucher.getCreationDateTime())
                .currencyId(voucher.getCurrencyId())
                .description(voucher.getDescription())
                .idTransaccion(voucher.getIdTransaccion())
                .installments(voucher.getInstallments())
                .paymentTypeId(voucher.getPaymentTypeId())
                .status(voucher.getStatus())
                .statusDetail(voucher.getStatusDetail())
                .transactionAmount(voucher.getTransactionAmount())
                .user(this.mapToUser(voucher.getUser()))
                .initPoint(voucher.getInitPoint())
                .build();
    }

    private UserPaymentResponse mapToUser(UserEntity user) {
        return UserPaymentResponse.builder()
                .name(user.getName())
                .country(user.getCountry())
                .title(user.getTitle())
                .itsVip(user.isItsVip())
                .lastname(user.getLastname())
                .username(user.getUsername())
                .specialty(user.getSpecialty())
                .profile(null)
                .build();
    }


}
