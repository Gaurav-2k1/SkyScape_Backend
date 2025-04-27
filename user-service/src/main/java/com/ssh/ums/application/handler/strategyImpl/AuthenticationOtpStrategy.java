package com.ssh.ums.application.handler.strategyImpl;


import com.ssh.dtos.TokenObject;
import com.ssh.exceptions.NotFoundException;
import com.ssh.redis.RedisService;
import com.ssh.ums.application.constants.Constants;
import com.ssh.ums.application.constants.LookUpConstants;
import com.ssh.ums.application.enums.FunctionalAreaEnum;
import com.ssh.ums.application.handler.strategy.FunctionalOtpStrategy;
import com.ssh.ums.application.service.interfaces.*;
import com.ssh.ums.domain.entity.auth.Login;
import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.dto.cache.LoginUserTempCache;
import com.ssh.ums.dto.cache.UserProfileTempCache;
import com.ssh.ums.dto.login.CreateLogin;
import com.ssh.ums.dto.otp.*;
import com.ssh.ums.dto.user.CreateUserDto;
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
    private final IDeviceDetailsService deviceDetailsService;
    private final ISessionService sessionService;

    @Override
    public String getPurpose() {
        return String.valueOf(FunctionalAreaEnum.FUNCTIONAL_AREA_LOGIN);
    }


    @Override
    public GenerateOtpResponse sendOtp(GenerateOtpRequest request) {
        GenerateOtpResponse otpResponse = otpService.generateOtpAndProcess(request);

        String email = request.getEmail();
        String mobile = request.getMobile();
        UUID referenceId;
        LoginUserTempCache tempCache = LoginUserTempCache.builder().build();

        // Check if user exists
        UserProfile user = userService.checkUserProfileFromEmailOrMobile(email, mobile);

        if (user == null) {
            // For new user, cache temp user profile
            UserProfileTempCache tempProfile = UserProfileTempCache.builder()
                    .emailId(email)
                    .mobileNumber(mobile)
                    .authMethod(Constants.AUTH_METHOD_OTP)
                    .twoReferenceId(otpResponse.getReferenceId())
                    .build();

            String tempProfileCacheKey = "USER_TEMP:" + tempProfile.getReferenceId();
            redisService.save(tempProfileCacheKey, tempProfile, 30, TimeUnit.MINUTES);
            referenceId = tempProfile.getReferenceId();

            tempCache.setIsUser(false);
        } else {
            // For existing user, create login request
            Login login = loginService.createLoginRequest(CreateLogin.builder()
                    .authMethod(Constants.AUTH_METHOD_OTP)
                    .ipAddress(deviceDetailsService.extractDeviceDetails().getIpAddress())
                    .deviceInfo(deviceDetailsService.extractDeviceDetails().getBrowser())
                    .statusLookup(LookUpConstants.STATUS_PENDING)
                    .twoReferenceId(otpResponse.getReferenceId())
                    .build(), user);
            referenceId = login.getReferenceId();

            tempCache.setIsUser(true);
        }

        // Save reference cache for validation
        String referenceCacheKey = "USER_REFERENCE:" + referenceId;
        redisService.save(referenceCacheKey, tempCache, 30, TimeUnit.MINUTES);

        return GenerateOtpResponse.builder()
                .referenceId(referenceId)
                .otp(otpResponse.getOtp())
                .build();
    }

    @Override
    public ValidateOtpResponse validateOtp(ValidateOtpRequest request) {
        String referenceCacheKey = "USER_REFERENCE:" + request.getReferenceId();

        LoginUserTempCache tempCache = redisService.get(referenceCacheKey, LoginUserTempCache.class)
                .orElseThrow(() -> new NotFoundException("Invalid Reference Id"));

        boolean isExistingUser = tempCache.getIsUser();
        Login login = null;
        String tempUserCacheKey = "USER_TEMP:" + request.getReferenceId();

        if (isExistingUser) {
            login = loginService.getLoginRequest(request.getReferenceId());
            request.setReferenceId(login.getTwoReferenceId());
        } else {
            UserProfileTempCache tempProfile = redisService.get(tempUserCacheKey, UserProfileTempCache.class)
                    .orElseThrow(() -> new NotFoundException("Invalid Reference Id"));
            request.setReferenceId(tempProfile.getTwoReferenceId());
        }

        ValidateOtpResponse otpValidationResponse = otpService.validateOtpAndProcess(request);

        if (isExistingUser) {
            login.setStatusLookup(LookUpConstants.STATUS_VALIDATED);
            loginService.saveLoginRequest(login);
        } else {
            UserProfileTempCache tempProfile = redisService.get(tempUserCacheKey, UserProfileTempCache.class)
                    .orElseThrow(() -> new NotFoundException("Invalid Reference Id"));

            UserProfile savedUser = userService.createUserProfile(CreateUserDto.builder()
                    .email(tempProfile.getEmailId())
                    .firstName(tempProfile.getFirstName())
                    .lastName(tempProfile.getLastName())
                    .mobileNumber(tempProfile.getMobileNumber())
                    .build());

            login = loginService.createLoginRequest(CreateLogin.builder()
                    .statusLookup(LookUpConstants.STATUS_VALIDATED)
                    .ipAddress(deviceDetailsService.extractDeviceDetails().getIpAddress())
                    .deviceInfo(deviceDetailsService.extractDeviceDetails().getBrowser())
                    .authMethod(Constants.AUTH_METHOD_OTP)
                    .twoReferenceId(tempProfile.getTwoReferenceId())
                    .build(), savedUser);

        }

        // Optionally: Generate and return session token here

        TokenObject tokenObject = sessionService.createSession(login);
        otpValidationResponse.setResponse(tokenObject);
        return otpValidationResponse;
    }

    @Override
    public RegenerateOtpResponse regenerateOtpAndProcess(RegenerateOtpRequest request) {
        Login login = loginService.getLoginRequest(request.getReferenceId());
        request.setReferenceId(login.getTwoReferenceId());

        return otpService.regenerateOtpAndProcess(request);
    }


}
