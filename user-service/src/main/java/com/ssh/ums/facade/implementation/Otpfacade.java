package com.ssh.ums.facade.implementation;

import com.ssh.ums.application.handler.resolver.OtpStrategyResolver;
import com.ssh.ums.application.handler.strategy.FunctionalOtpStrategy;
import com.ssh.ums.dto.otp.*;
import com.ssh.ums.facade.interfaces.IOtpFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Otpfacade implements IOtpFacade {

    private final OtpStrategyResolver strategyResolver;

    @Override
    public GenerateOtpResponse generateOtpAndProcess(GenerateOtpRequest generateOtpRequest) {
        FunctionalOtpStrategy strategy = strategyResolver.resolve(generateOtpRequest.getFunctionalArea());
        return strategy.sendOtp(generateOtpRequest);
    }

    @Override
    public ValidateOtpResponse validateOtpAndProcess(ValidateOtpRequest validateOtpRequest) {
        FunctionalOtpStrategy strategy = strategyResolver.resolve(validateOtpRequest.getFunctionalArea());
        return strategy.validateOtp(validateOtpRequest);
    }

    @Override
    public RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest regenerateOtpRequest) {
        FunctionalOtpStrategy strategy = strategyResolver.resolve(regenerateOtpRequest.getFunctionalArea());
        return strategy.regenerateOtpAndProcess(regenerateOtpRequest);
    }
}
