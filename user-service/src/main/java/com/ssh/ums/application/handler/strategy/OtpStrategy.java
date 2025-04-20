package com.ssh.ums.application.handler.strategy;

import com.ssh.ums.dto.otp.*;

public interface OtpStrategy {
    GenerateOtpResponse sendOtp(GenerateOtpRequest generateOtpRequest);

    ValidateOtpResponse validateOtp(ValidateOtpRequest validateOtpRequest);

    RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest);

}
