package com.ums.dto.otp;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class GenerateOtpResponse {
    private UUID referenceId;
    private String otp;
}
