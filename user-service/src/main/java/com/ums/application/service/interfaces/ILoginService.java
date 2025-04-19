package com.ums.application.service.interfaces;

import com.ums.domain.entity.Login;
import com.ums.dto.login.CreateLogin;

import java.util.UUID;

public interface ILoginService {
    Login createLoginRequest(CreateLogin createLogin);

    Login saveLoginRequest(Login login);

    Login getLoginRequest(UUID referenceId);
}
