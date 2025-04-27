package com.ssh.ums.application.service.impl;

import com.ssh.exceptions.NotFoundException;
import com.ssh.ums.application.service.interfaces.ILoginService;
import com.ssh.ums.domain.entity.auth.Login;
import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.domain.repo.auth.LoginRepo;
import com.ssh.ums.dto.login.CreateLogin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class LoginService implements ILoginService {

    private final LoginRepo loginRepo;


    @Override
    public Login createLoginRequest(CreateLogin createLogin, UserProfile userProfile) {
        Login login = Login.builder()
                .authMethod(createLogin.getAuthMethod())
                .statusLookup(createLogin.getStatusLookup())
                .ipAddress(createLogin.getIpAddress())
                .deviceInfo(createLogin.getDeviceInfo())
                .userProfile(userProfile)
                .twoReferenceId(createLogin.getTwoReferenceId())
                .build();
        return saveLoginRequest(login);
    }

    @Override
    public Login saveLoginRequest(Login login) {
        return loginRepo.save(login);
    }

    @Override
    public Login getLoginRequest(UUID referenceId) {
        return loginRepo.findByReferenceId(referenceId)
                .orElseThrow(() -> new NotFoundException("Invalid login request"));
    }
}
