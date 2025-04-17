package com.ums.facade.implementation;

import com.ums.dto.otp.GenerateOtpRequest;
import com.ums.dto.otp.GenerateOtpResponse;
import com.ums.dto.otp.ValidateOtpRequest;
import com.ums.dto.otp.ValidateOtpResponse;
import com.ums.facade.interfaces.IOtpFacade;

public class Otpfacade implements IOtpFacade {
    @Override
    public GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest) {
        return null;
    }

    @Override
    public ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest) {
        return null;
    }
}
