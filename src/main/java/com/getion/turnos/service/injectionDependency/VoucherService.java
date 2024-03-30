package com.getion.turnos.service.injectionDependency;

import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.model.response.VoucherResponse;

import java.util.List;

public interface VoucherService {
    void save(Voucher voucher);

    List<VoucherResponse> getAllVouchersByUserId(Long userId);
}
