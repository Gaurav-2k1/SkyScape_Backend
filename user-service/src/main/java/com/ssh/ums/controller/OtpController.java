package com.ssh.ums.controller;

import com.ssh.response.ApiResponse;
import com.ssh.ums.dto.otp.*;
import com.ssh.utils.ResponseUtil;
import com.ssh.ums.controller.interfaces.IOtpController;
import com.ssh.ums.facade.interfaces.IOtpFacade;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController

//@RequestMapping("/v1/otp")
@AllArgsConstructor
public class OtpController implements IOtpController {

    private final IOtpFacade otpFacade;

    @Override
    public ResponseEntity<ApiResponse<GenerateOtpResponse>> generateOtp(GenerateOtpRequest generateOtpRequest) {
        GenerateOtpResponse response = otpFacade.generateOtpAndProcess(generateOtpRequest);
        return ResponseUtil.success(response, "Otp Sent Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<Object>> validateOtp(ValidateOtpRequest validateOtpRequest) {
        ValidateOtpResponse response = otpFacade.validateOtpAndProcess(validateOtpRequest);
        return ResponseUtil.success(
                response,
                "Otp Validated Successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ApiResponse<RegenerateOtpResponse>> resendOtp(RegenerateOtpRequest regenerateOtpRequest) {
        RegenerateOtpResponse response = otpFacade.regenerateOtpAndProcess(regenerateOtpRequest);
        return ResponseUtil.success(
                response,
                "Otp Resent Sent Successfully", HttpStatus.OK);
    }
}
