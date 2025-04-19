package com.ums.application.service.interfaces;

import com.ums.dto.otp.*;

public interface IOtpService {
    GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest);

    ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest);

    RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest);
}
