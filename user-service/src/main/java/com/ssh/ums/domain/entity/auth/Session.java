package com.ssh.ums.domain.entity.auth;

import com.ssh.entity.BaseEntity;
import com.ssh.ums.application.enums.SessionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Session extends BaseEntity {
    @OneToOne
    private Login login;
    @Enumerated(EnumType.STRING)
    private SessionStatus status;
    private Boolean isCurrent;
    private String location;
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private UUID sessionId = UUID.randomUUID();
}
