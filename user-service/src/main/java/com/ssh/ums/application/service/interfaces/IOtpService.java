package com.ssh.ums.application.service.interfaces;

import com.ssh.ums.dto.otp.*;

public interface IOtpService {
    GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest);

    ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest);

    RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest);
}
