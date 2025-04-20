package com.ssh.ums.application.service.impl;

import com.ssh.exceptions.NotFoundException;
import com.ssh.ums.application.service.interfaces.IUserService;
import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.domain.repo.user.UserProfileRepo;
import com.ssh.ums.dto.user.CreateUserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final UserProfileRepo userProfileRepo;

    @Override
    public UserProfile getUserProfileFromEmailOrMobile(String email, String mobile) {
        return userProfileRepo.findByMobileNumberOrEmailId(mobile, email)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
    }

    @Override
    public UserProfile checkUserProfileFromEmailOrMobile(String email, String mobile) {
        return userProfileRepo.findByMobileNumberOrEmailId(mobile, email)
                .orElse(null);
    }

    @Override
    public UserProfile createUserProfile(CreateUserDto userDto) {
        UserProfile userProfile = UserProfile.builder()
                .emailId(userDto.getEmail())
                .mobileNumber(userDto.getMobileNumber())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
        return userProfileRepo.save(userProfile);
    }

    @Override
    public UserProfile saveUserProfile(UserProfile userProfile) {

        return userProfileRepo.save(userProfile);
    }
}
