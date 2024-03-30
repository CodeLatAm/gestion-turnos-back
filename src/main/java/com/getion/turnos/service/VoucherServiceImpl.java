package com.getion.turnos.service;

import com.getion.turnos.mapper.VoucherMapper;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.model.response.VoucherResponse;
import com.getion.turnos.repository.VoucherRepository;
import com.getion.turnos.service.injectionDependency.UserService;
import com.getion.turnos.service.injectionDependency.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final UserService userService;
    private final VoucherMapper voucherMapper;

    @Transactional
    @Override
    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public List<VoucherResponse> getAllVouchersByUserId(Long userId) {
        UserEntity user = userService.findById(userId);
        List<Voucher> vouchers = voucherRepository.findByUserId(userId);
        List<VoucherResponse> responses = voucherMapper.mapToVouchers(vouchers);
        return responses;
    }
}
