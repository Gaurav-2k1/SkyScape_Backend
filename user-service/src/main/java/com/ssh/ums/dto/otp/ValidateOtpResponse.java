package com.ssh.ums.dto.otp;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ValidateOtpResponse {
    private Object response;
    private Boolean validated;
}
