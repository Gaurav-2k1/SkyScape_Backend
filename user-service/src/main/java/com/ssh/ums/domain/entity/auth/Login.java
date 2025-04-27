package com.ssh.ums.domain.entity.auth;

import com.ssh.entity.BaseEntity;
import com.ssh.ums.domain.entity.user.UserProfile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Login extends BaseEntity {
    @Builder.Default
    private UUID referenceId = UUID.randomUUID();
    private UUID twoReferenceId;
    private String statusLookup;
    private String authMethod;
    @Column(nullable = false, updatable = false)

    private String ipAddress;

    @Column(nullable = false, updatable = false)
    private String deviceInfo;
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    private UserProfile userProfile;
}
