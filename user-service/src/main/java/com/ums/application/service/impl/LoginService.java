package com.ums.application.service.impl;

import com.ssh.exceptions.NotFoundException;
import com.ums.application.constants.LookUpConstants;
import com.ums.application.service.interfaces.ILoginService;
import com.ums.domain.entity.Login;
import com.ums.domain.repo.LoginRepo;
import com.ums.dto.login.CreateLogin;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@AllArgsConstructor
public class LoginService implements ILoginService {

    private final LoginRepo loginRepo;


    @Override
    public Login createLoginRequest(CreateLogin createLogin) {
        Login login = Login.builder()
                .authMethod(createLogin.getAuthMethod())
                .statusLookup(LookUpConstants.STATUS_PENDING)
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
