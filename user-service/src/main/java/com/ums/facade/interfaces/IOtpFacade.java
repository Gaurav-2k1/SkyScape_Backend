package com.ums.facade.interfaces;

import com.ums.dto.otp.GenerateOtpRequest;
import com.ums.dto.otp.GenerateOtpResponse;
import com.ums.dto.otp.ValidateOtpRequest;
import com.ums.dto.otp.ValidateOtpResponse;

public interface IOtpFacade {

    GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest);

    ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest);
}
