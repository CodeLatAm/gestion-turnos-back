package com.getion.turnos.repository;

import com.getion.turnos.model.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    List<Voucher> findByUserIdOrderByCreationDateTimeDesc(Long userId);

    Optional<Voucher> findByIdAndUserId(Long voucherId, Long userId);

    Optional<Voucher> findByOrderReferenceExternal(String purchaseReference);
}
