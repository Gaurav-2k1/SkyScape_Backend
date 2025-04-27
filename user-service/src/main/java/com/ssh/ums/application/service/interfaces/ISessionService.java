package com.ssh.ums.application.service.interfaces;

import com.ssh.dtos.TokenObject;
import com.ssh.ums.domain.entity.auth.Login;
import com.ssh.ums.domain.entity.auth.Session;
import com.ssh.ums.domain.entity.user.UserProfile;

import java.util.List;

public interface ISessionService {
    TokenObject createSession(Login login);

    Session getSession(String sessionId);

    Session getSession(Login login);

    List<Session> getSession(UserProfile userProfile);

    void logout();

    void logoutAllDevices();
}
