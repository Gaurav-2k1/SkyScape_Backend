package com.ums.controller.interfaces;

import com.ssh.response.ApiResponse;
import com.ums.dto.otp.GenerateOtpRequest;
import com.ums.dto.otp.GenerateOtpResponse;
import com.ums.dto.otp.ValidateOtpRequest;
import com.ums.dto.otp.ValidateOtpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface IOtpController {

    @PostMapping("/send")
    ResponseEntity<ApiResponse<GenerateOtpResponse>> generateOtp(@RequestBody GenerateOtpRequest generateOtpRequest);

    @PostMapping("/validate")
    ResponseEntity<ApiResponse<ValidateOtpResponse>> validateOtp(@RequestBody ValidateOtpRequest validateOtpRequest);
}
