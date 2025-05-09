package com.ssh.ums.application.service.interfaces;

import com.ssh.ums.domain.entity.auth.Login;
import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.dto.login.CreateLogin;

import java.util.UUID;

public interface ILoginService {
    Login createLoginRequest(CreateLogin createLogin, UserProfile userProfile);

    Login saveLoginRequest(Login login);

    Login getLoginRequest(UUID referenceId);
}
