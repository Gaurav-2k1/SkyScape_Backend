package com.ums.dto.otp;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GenerateOtpRequest {

    private UUID referenceId;
    @NotNull(message = "Functional Area should not be null")
    private String functionalArea;
    @NotNull(message = "Functional Area should not be null")
    private String deliveryChannel;
    @Email
    private String email;
    private String mobile;
}
