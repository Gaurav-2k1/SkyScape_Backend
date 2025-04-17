package com.ums.controller;

import com.ssh.response.ApiResponse;
import com.ums.controller.interfaces.IOtpController;
import com.ums.dto.otp.GenerateOtpRequest;
import com.ums.dto.otp.GenerateOtpResponse;
import com.ums.dto.otp.ValidateOtpRequest;
import com.ums.dto.otp.ValidateOtpResponse;
import com.ums.facade.interfaces.IOtpFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/otp")
@AllArgsConstructor
public class OtpController implements IOtpController {

    private final IOtpFacade otpFacade;

    @Override
    public ResponseEntity<ApiResponse<GenerateOtpResponse>> generateOtp(GenerateOtpRequest generateOtpRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<ValidateOtpResponse>> validateOtp(ValidateOtpRequest validateOtpRequest) {
        return null;
    }
}
