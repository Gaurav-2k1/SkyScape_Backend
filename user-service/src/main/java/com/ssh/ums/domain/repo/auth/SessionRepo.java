package com.ssh.ums.domain.repo.auth;

import com.ssh.ums.domain.entity.auth.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
}
