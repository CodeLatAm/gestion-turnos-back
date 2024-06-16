package com.getion.turnos.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private OffsetDateTime creationDateTime;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private OffsetDateTime approvalDateTime;
    private String description;
    private String statusDetail;
    private String currencyId;
    private int installments;// Cuotas
    private BigDecimal transactionAmount;
    private String status;
    private String paymentTypeId;
    @Transient
    private String initPoint;
    @Column(name = "order_reference_external")
    private String orderReferenceExternal;
    @ManyToOne
    private UserEntity user;
}
