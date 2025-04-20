package com.ssh.ums.domain.repo.otp;

import com.ssh.ums.domain.entity.otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp, Long> {
}
