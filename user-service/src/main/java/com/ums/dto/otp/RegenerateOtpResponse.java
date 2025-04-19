package com.ums.dto.otp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegenerateOtpResponse {
    private String referenceId;
    private String otp;

}
