package com.ssh.ums.application.service.impl;

import com.ssh.dtos.TokenObject;
import com.ssh.redis.RedisService;
import com.ssh.ums.application.enums.SessionStatus;
import com.ssh.ums.application.service.interfaces.ISessionService;
import com.ssh.ums.domain.entity.auth.Login;
import com.ssh.ums.domain.entity.auth.Session;
import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.domain.repo.auth.SessionRepo;
import com.ssh.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService {

    private final RedisService redisService;
    private final JwtUtils jwtUtils;
    private final SessionRepo sessionRepo;

    @Override
    @Transactional
    public TokenObject createSession(Login login) {
        Session session = Session.builder()
                .login(login)
                .isCurrent(Boolean.TRUE)
                .location("location")
                .status(SessionStatus.ACTIVE)
                .build();

        Session savedSession = sessionRepo.save(session);

        String userId = String.valueOf(savedSession.getLogin().getUserProfile().getUserId());

        TokenObject tokenObject = jwtUtils.generateToken(userId, String.valueOf(savedSession.getSessionId()));

        redisService.save(userId + ":" + session.getSessionId(), tokenObject, 30, TimeUnit.MINUTES);

        return tokenObject;
    }

    @Override
    public Session getSession(String sessionId) {
        return null;
    }

    @Override
    public Session getSession(Login login) {
        return null;
    }

    @Override
    public List<Session> getSession(UserProfile userProfile) {
        return List.of();
    }

    @Override
    public void logout() {

    }

    @Override
    public void logoutAllDevices() {

    }
}
