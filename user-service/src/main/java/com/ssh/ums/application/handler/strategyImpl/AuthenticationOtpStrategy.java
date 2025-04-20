package com.ssh.ums.application.handler.strategyImpl;


import com.ssh.exceptions.NotFoundException;
import com.ssh.redis.RedisService;
import com.ssh.ums.application.constants.Constants;
import com.ssh.ums.application.constants.LookUpConstants;
import com.ssh.ums.application.handler.strategy.FunctionalOtpStrategy;
import com.ssh.ums.application.service.interfaces.ILoginService;
import com.ssh.ums.application.service.interfaces.IOtpService;
import com.ssh.ums.application.service.interfaces.IUserService;
import com.ssh.ums.domain.entity.Login;
import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.dto.login.CreateLogin;
import com.ssh.ums.dto.login.LoginUserTempCache;
import com.ssh.ums.dto.otp.*;
import com.ssh.ums.dto.user.CreateUserDto;
import com.ssh.ums.dto.user.UserProfileTempCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class AuthenticationOtpStrategy implements FunctionalOtpStrategy {

    private final ILoginService loginService;
    private final IOtpService otpService;
    private final IUserService userService;
    private final RedisService redisService;

    @Override
    public String getPurpose() {
        return LookUpConstants.FUNCTIONAL_AREA_LOGIN;
    }

    @Override
    public GenerateOtpResponse sendOtp(GenerateOtpRequest generateOtpRequest) {
        GenerateOtpResponse response = otpService.generateOtpAndProcess(generateOtpRequest);
        String emailId = generateOtpRequest.getEmail();
        String mobileNumber = generateOtpRequest.getMobile();

        UUID referenceId;
        LoginUserTempCache userTempCache = LoginUserTempCache.builder().build();
        if (userService.checkUserProfileFromEmailOrMobile(emailId, mobileNumber) == null) {
            UserProfileTempCache userProfileTempCache = UserProfileTempCache.builder()
                    .emailId(generateOtpRequest.getEmail())
                    .mobileNumber(generateOtpRequest.getMobile())
                    .authMethod(Constants.AUTH_METHOD_OTP)
                    .twoReferenceId(response.getReferenceId())
                    .build();
            String cacheKey = "USER_TEMP:" + userProfileTempCache.getReferenceId();
            redisService.save(cacheKey, userProfileTempCache, 30, TimeUnit.MINUTES);
            referenceId = userProfileTempCache.getReferenceId();
            userTempCache.setIsUser(Boolean.FALSE);
        } else {
            Login login = loginService.createLoginRequest(CreateLogin.builder()
                    .authMethod(Constants.AUTH_METHOD_OTP)
                    .twoReferenceId(response.getReferenceId())
                    .build());
            referenceId = login.getReferenceId();
            userTempCache.setIsUser(Boolean.TRUE);
        }

        String requestCache = "USER_REFERENCE:" + referenceId;
        redisService.save(requestCache, userTempCache, 30, TimeUnit.MINUTES);

        return GenerateOtpResponse.builder()
                .referenceId(referenceId)
                .otp(response.getOtp())
                .build();
    }

    @Override
    public ValidateOtpResponse validateOtp(ValidateOtpRequest validateOtpRequest) {
        String requestCache = "USER_REFERENCE:" + validateOtpRequest.getReferenceId();

        boolean isUser = redisService.get(requestCache, LoginUserTempCache.class).orElseThrow(
                        () -> new NotFoundException("Invalid Reference Id")
                )
                .getIsUser();
        Login loginRequest = null;
        String userTempCacheKey = "USER_TEMP:" + validateOtpRequest.getReferenceId();

        if (isUser) {
            loginRequest = loginService.getLoginRequest(validateOtpRequest.getReferenceId());
            validateOtpRequest.setReferenceId(loginRequest.getTwoReferenceId());
        } else {

            UserProfileTempCache userProfileTemp = redisService.get(userTempCacheKey, UserProfileTempCache.class)
                    .orElseThrow(() -> new NotFoundException("Invalid Reference Id"));
            validateOtpRequest.setReferenceId(userProfileTemp.getTwoReferenceId());
        }

        ValidateOtpResponse response = otpService.validateOtpAndProcess(validateOtpRequest);

        if (isUser) {
            loginRequest.setStatusLookup(LookUpConstants.STATUS_VALIDATED);
            loginService.saveLoginRequest(loginRequest);
        } else {


            UserProfileTempCache userProfileTemp = redisService.get(userTempCacheKey, UserProfileTempCache.class)
                    .orElseThrow(() -> new NotFoundException("Invalid Reference Id"));
            UserProfile userProfile = userService.createUserProfile(CreateUserDto.builder()
                    .email(userProfileTemp.getEmailId())
                    .firstName(userProfileTemp.getFirstName())
                    .lastName(userProfileTemp.getLastName())
                    .mobileNumber(userProfileTemp.getMobileNumber())
                    .build());

        }


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
