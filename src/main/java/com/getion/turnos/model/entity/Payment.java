package com.getion.turnos.model.entity;

import com.getion.turnos.enums.PaymentEnum;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_pago")
    private PaymentEnum paymentStatus;
    private BigDecimal total;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_creacion")
    private LocalDate dateCreated;
    @Temporal(TemporalType.DATE)
    @Column(name = "ultima_actualizacion")
    private LocalDate lastUpdate;
    @Column(name = "order_reference_external")
    private String orderReferenceExternal;
    @Column(name = "preference_id_mpago")
    private String preferenceIdPaymentMPago;
    @ManyToOne()
    private UserEntity user;
}
