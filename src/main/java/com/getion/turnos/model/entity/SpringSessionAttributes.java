package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "SPRING_SESSION_ATTRIBUTES")
public class SpringSessionAttributes implements Serializable {

    @Id
    @Column(name = "SESSION_PRIMARY_ID", nullable = false)
    private String sessionPrimaryId;

    @Id
    @Column(name = "ATTRIBUTE_NAME", length = 200, nullable = false)
    private String attributeName;

    @Column(name = "ATTRIBUTE_BYTES", nullable = false)
    private byte[] attributeBytes;

    @ManyToOne
    @JoinColumn(name = "SESSION_PRIMARY_ID", referencedColumnName = "PRIMARY_ID", insertable = false, updatable = false)
    private SpringSession springSession;
}
