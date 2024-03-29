package com.getion.turnos.service;

import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.repository.VoucherRepository;
import com.getion.turnos.service.injectionDependency.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    @Override
    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }
}
