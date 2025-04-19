package com.ums.application.handler.strategyImpl;


import com.ums.application.constants.Constants;
import com.ums.application.constants.LookUpConstants;
import com.ums.application.handler.strategy.FunctionalOtpStrategy;
import com.ums.application.service.interfaces.ILoginService;
import com.ums.application.service.interfaces.IOtpService;
import com.ums.domain.entity.Login;
import com.ums.dto.login.CreateLogin;
import com.ums.dto.otp.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthenticationOtpStrategy implements FunctionalOtpStrategy {

    private final ILoginService loginService;
    private final IOtpService otpService;

    @Override
    public String getPurpose() {
        return LookUpConstants.FUNCTIONAL_AREA_LOGIN;
    }

    @Override
    public GenerateOtpResponse sendOtp(GenerateOtpRequest generateOtpRequest) {
        GenerateOtpResponse response = otpService.generateOtpAndProcess(generateOtpRequest);
        Login login = loginService.createLoginRequest(CreateLogin.builder()
                .authMethod(Constants.AUTH_METHOD_OTP)
                .twoReferenceId(response.getReferenceId())
                .build());
        return GenerateOtpResponse.builder()
                .referenceId(login.getReferenceId())
                .otp(response.getOtp())
                .build();
    }

    @Override
    public ValidateOtpResponse validateOtp(ValidateOtpRequest validateOtpRequest) {
        Login loginRequest = loginService.getLoginRequest(validateOtpRequest.getReferenceId());
        validateOtpRequest.setReferenceId(loginRequest.getTwoReferenceId());
        ValidateOtpResponse response = otpService.validateOtpAndProcess(validateOtpRequest);
        loginRequest.setStatusLookup(LookUpConstants.STATUS_VALIDATED);
        loginService.saveLoginRequest(loginRequest);
        // Session Token provide
        return response;
    }

    @Override
    public RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest) {
        Login login = loginService.getLoginRequest(regenerateOtpRequest.getReferenceId());
        regenerateOtpRequest.setReferenceId(login.getTwoReferenceId());
        return otpService.regenerateOtpAndProcess(regenerateOtpRequest);
    }
}
