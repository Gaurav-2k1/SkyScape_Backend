package com.ssh.ums.domain.repo.otp;

import com.ssh.ums.domain.entity.otp.OtpRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OtpRequestRepo extends JpaRepository<OtpRequest, Long> {
    Optional<OtpRequest> findByReferenceId(UUID referenceId);
}
