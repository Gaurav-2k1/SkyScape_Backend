package com.ums.dto.otp;

import com.ums.domain.entity.otp.Otp;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;


@Data
@Builder
public class GenerateOtpResponseBuilder {
    HashMap<String, Otp> otpHashMap;
    String otp;
}
