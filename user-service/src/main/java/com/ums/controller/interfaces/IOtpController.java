package com.ums.controller.interfaces;

import com.ssh.response.ApiResponse;
import com.ums.dto.otp.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequestMapping("/v1/otp")
public interface IOtpController {

    @PostMapping("/send")
    ResponseEntity<ApiResponse<GenerateOtpResponse>> generateOtp(@RequestBody @Valid GenerateOtpRequest generateOtpRequest);

    @PostMapping("/validate")
    ResponseEntity<ApiResponse<Object>> validateOtp(@RequestBody @Valid ValidateOtpRequest validateOtpRequest);

    @PostMapping("/resend")
    ResponseEntity<ApiResponse<RegenerateOtpResponse>> resendOtp(@RequestBody @Valid RegenerateOtpRequest regenerateOtpRequest);
}
