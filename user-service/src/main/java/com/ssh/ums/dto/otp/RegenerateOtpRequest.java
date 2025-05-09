package com.ssh.ums.dto.otp;


import com.ssh.ums.annotations.interfaces.ValidDeliveryChannel;
import com.ssh.ums.annotations.interfaces.ValidFunctionalArea;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RegenerateOtpRequest {
    @NotNull(message = "ReferenceId should not be null")
    private UUID referenceId;
    @ValidDeliveryChannel
    private String deliveryChannel;
    @ValidFunctionalArea
    private String functionalArea;
}
