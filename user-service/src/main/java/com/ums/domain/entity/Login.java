package com.ums.domain.entity;

import com.ssh.entity.BaseEntity;
import jakarta.persistence.Entity;
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
    @PrePersist
    public void ensureReferenceId(){
        if(referenceId==null){
            referenceId = UUID.randomUUID();
        }
    }
}
