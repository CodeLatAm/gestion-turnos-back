package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.model.request.VoucherRequest;
import com.getion.turnos.model.request.VoucherUpdateRequest;
import com.getion.turnos.model.response.VoucherResponse;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.util.List;

public interface VoucherService {
    void save(Voucher voucher);

    List<VoucherResponse> getAllVouchersByUserId(Long userId);
    public void createVouchersForVipUsers();

    void updateVoucherOnPaymentSuccess(Voucher voucher);

    VoucherResponse updatePayment(VoucherUpdateRequest request) throws MPException, MPApiException;
}
