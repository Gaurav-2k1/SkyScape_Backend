package com.ssh.ums.dto.otp;

import com.ssh.ums.annotations.interfaces.ValidDeliveryChannel;
import com.ssh.ums.annotations.interfaces.ValidFunctionalArea;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Builder
@Data
public class ValidateOtpRequest {
    @NotNull(message = "ReferenceId should not be null")
    private UUID referenceId;
    @NotNull(message = "Otp should not be null")
    @Size(min = 6, max = 6)
    private String otp;
    @ValidDeliveryChannel
    private String deliveryChannel;
    @ValidFunctionalArea
    private String functionalArea;
}
