package com.ums.domain.entity.otp;


import com.ssh.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest extends BaseEntity {
    @Builder.Default
    private UUID referenceId = UUID.randomUUID();
    private Integer incorrectAttempts;
    private String statusLookup;
    private String functionalArea;
    private String deliveryChannel;
    @OneToMany(mappedBy = "otpRequest")
    private Map<String, Otp> otpHashMap;

    @PrePersist
    public void ensureReferenceId(){
        if(referenceId==null){
            referenceId = UUID.randomUUID();
        }
    }
}
