package com.ssh.ums.domain.entity;

import com.ssh.entity.BaseEntity;
import com.ssh.ums.domain.entity.user.UserProfile;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
    private String ipAddress;
    @ManyToOne
    private UserProfile userProfile;

    @PrePersist
    public void ensureReferenceId() {
        if (referenceId == null) {
            referenceId = UUID.randomUUID();
        }
    }
}
