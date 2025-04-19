package com.ums.application.handler.strategy;

import com.ums.dto.otp.*;

public interface OtpStrategy {
    GenerateOtpResponse sendOtp(GenerateOtpRequest generateOtpRequest);

    ValidateOtpResponse validateOtp(ValidateOtpRequest validateOtpRequest);

    RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest);

}
