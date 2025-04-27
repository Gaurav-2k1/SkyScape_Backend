package com.ssh.ums.domain.entity.otp;


import com.ssh.entity.BaseEntity;
import com.ssh.ums.application.enums.DeliveryChannelEnum;
import com.ssh.ums.application.enums.FunctionalAreaEnum;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private FunctionalAreaEnum functionalArea;
    @Enumerated(EnumType.STRING)
    private DeliveryChannelEnum deliveryChannel;
    @OneToMany(mappedBy = "otpRequest")
    private Map<String, Otp> otpHashMap;

    @PrePersist
    public void ensureReferenceId() {
        if (referenceId == null) {
            referenceId = UUID.randomUUID();
        }
    }
}
