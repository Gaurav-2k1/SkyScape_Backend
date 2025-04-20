package com.ssh.ums.domain.entity.otp;


import com.ssh.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Otp extends BaseEntity {

    @ManyToOne
    private OtpRequest otpRequest;
    private String otpCode;
    private String deliveryChannel;
    private String status;
    private String contactInfo;
}