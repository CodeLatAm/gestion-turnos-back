package com.getion.turnos.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "SPRING_SESSION")
public class SpringSession  implements Serializable {

    @Id
    @Column(name = "PRIMARY_ID", nullable = false)
    private String primaryId;

    @Column(name = "SESSION_ID", nullable = false)
    private String sessionId;

    @Column(name = "CREATION_TIME", nullable = false)
    private Long creationTime;

    @Column(name = "LAST_ACCESS_TIME", nullable = false)
    private Long lastAccessTime;

    @Column(name = "MAX_INACTIVE_INTERVAL", nullable = false)
    private Integer maxInactiveInterval;

    @Column(name = "EXPIRY_TIME", nullable = false)
    private Long expiryTime;

    @Column(name = "PRINCIPAL_NAME", length = 100)
    private String principalName;

}
