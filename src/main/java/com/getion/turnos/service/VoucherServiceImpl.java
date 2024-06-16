package com.getion.turnos.service;

import com.getion.turnos.exception.VoucherNotFoundException;
import com.getion.turnos.mapper.VoucherMapper;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.entity.Voucher;
import com.getion.turnos.model.request.VoucherRequest;
import com.getion.turnos.model.request.VoucherUpdateRequest;
import com.getion.turnos.model.response.VoucherResponse;
import com.getion.turnos.repository.VoucherRepository;
import com.getion.turnos.service.injectionDependency.MercadoPagoService;
import com.getion.turnos.service.injectionDependency.UserService;
import com.getion.turnos.service.injectionDependency.VoucherService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log
@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;
    private final UserService userService;
    private final VoucherMapper voucherMapper;
    private final MercadoPagoService mercadoPagoService;


    @Transactional
    @Override
    public void save(Voucher voucher) {
        voucherRepository.save(voucher);
    }

    @Override
    public List<VoucherResponse> getAllVouchersByUserId(Long userId) {
        UserEntity user = userService.findById(userId);
        List<Voucher> vouchers = voucherRepository.findByUserIdOrderByCreationDateTimeDesc(userId);

        List<VoucherResponse> responses = voucherMapper.mapToVouchers(vouchers);
        return responses;
    }
    @Override
    public void createVouchersForVipUsers(){
        // Obtengo la lista de usuarios VIP
        List<UserEntity> vipUsers = userService.findByItsVip(true);
        // Crear un nuevo comprobante de pago para cada usuario VIP
        for(UserEntity user: vipUsers){
            Voucher voucher = new Voucher();
            voucher.setCreationDateTime(OffsetDateTime.now());
            voucher.setTransactionAmount(BigDecimal.valueOf(5000));
            voucher.setStatus("pending");
            voucher.setDescription("Abonar cuota plan pro");
            voucher.setUser(user);
            voucher.setOrderReferenceExternal(UUID.randomUUID().toString());
            voucherRepository.save(voucher);
        }
    }

    @Override
    public void updateVoucherOnPaymentSuccess(Voucher voucher) {

    }

    @Transactional
    @Override
    public VoucherResponse updatePayment(VoucherUpdateRequest request) throws MPException, MPApiException {
        log.info("Entrando al metodo updatePayment() en VoucherServiceImpl ");
        Optional<Voucher> voucher = voucherRepository.findByIdAndUserId(request.getVoucherId(), request.getUserId());
        log.info("Referencia de voucher: " + voucher.get().getOrderReferenceExternal().toString());
        if(voucher.isPresent()){
            Voucher voucherUpdate = voucher.get();
            voucherUpdate.setInitPoint(mercadoPagoService.updatePayment(voucherUpdate));
            voucherRepository.save(voucherUpdate);
            VoucherResponse response = voucherMapper.mapToVoucherResponse(voucherUpdate);
            voucherRepository.save(voucherUpdate);
            return response;
        }else {
            log.info("Voucher no encontrado con datos: " + request.getVoucherId() + "y" + request.getUserId());
            throw new VoucherNotFoundException("Voucher no encontrado con datos: " + request.getVoucherId() + "y" + request.getUserId());
        }
    }
}
