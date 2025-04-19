package com.ums.dto.otp;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateOtpResponse {
    private String response;
    private Boolean validated;
}
