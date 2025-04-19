package com.ums.domain.repo;

import com.ums.domain.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoginRepo extends JpaRepository<Login, Long> {

    Optional<Login> findByReferenceId(UUID referenceId);
}
