package com.ssh.ums.domain.repo.user;

import com.ssh.ums.domain.entity.user.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepo extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByMobileNumberOrEmailId(String mobileNumber,String emailId);
}
