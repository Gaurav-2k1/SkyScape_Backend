package com.ums.domain.repo.otp;

import com.ums.domain.entity.otp.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepo extends JpaRepository<Otp, Long> {
}
