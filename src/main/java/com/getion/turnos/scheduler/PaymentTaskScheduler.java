package com.getion.turnos.scheduler;

import com.getion.turnos.service.injectionDependency.VoucherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Log
@Component
@RequiredArgsConstructor
public class PaymentTaskScheduler {

    private final VoucherService voucherService;
    /*  @PostConstruct
        public void init(){
            this.createMonthlyPaymentRecord();
    }*/
    // @Scheduled(cron = "0 0 0 1 * *") // Ejecutar el primer día de cada mes a las 12:00 AM
    //@Scheduled(cron = "0 0 20 * * *") // Ejecutar todos los días a las 8:00 PM
    @Scheduled(cron = "0 31 23 * * *")
    public void createMonthlyPaymentRecord() {
        log.info("Ejecutando createMonthlyPaymentRecord() " + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        voucherService.createVouchersForVipUsers();
    }
}
