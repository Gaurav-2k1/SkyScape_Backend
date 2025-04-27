package com.ssh.ums.domain.repo.auth;

import com.ssh.ums.domain.entity.auth.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginRepo extends JpaRepository<Login, Long> {

    Optional<Login> findByReferenceId(UUID referenceId);
}
