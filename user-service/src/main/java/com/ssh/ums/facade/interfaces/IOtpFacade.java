package com.ssh.ums.facade.interfaces;

import com.ssh.ums.dto.otp.*;

public interface IOtpFacade {

    GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest);

    ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest);

    RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest);
}
