package com.ums.dto.otp;


import com.ums.annotations.interfaces.ValidDeliveryChannel;
import com.ums.annotations.interfaces.ValidFunctionalArea;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class GenerateOtpRequest {

    private UUID referenceId;
    @ValidFunctionalArea
    @NotNull(message = "Functional Area should not be null")
    private String functionalArea;
    @ValidDeliveryChannel
    @NotNull(message = "Functional Area should not be null")
    private String deliveryChannel;
    @Email
    private String email;
    private String mobile;
}
