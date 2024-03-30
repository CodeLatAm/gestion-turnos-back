package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "proof_of_payment")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idTransaccion;
    private OffsetDateTime creationDateTime;
    private OffsetDateTime approvalDateTime;
    private String description;
    private String statusDetail;
    private String currencyId;
    private int installments;// Cuotas
    private BigDecimal transactionAmount;
    private String status;
    private String paymentTypeId;
    @ManyToOne
    private UserEntity user;
}
