package com.ssh.ums.application.service.interfaces;

import com.ssh.ums.domain.entity.user.UserProfile;
import com.ssh.ums.dto.user.CreateUserDto;

public interface IUserService {
    UserProfile getUserProfileFromEmailOrMobile(String email, String mobile);

    UserProfile checkUserProfileFromEmailOrMobile(String email, String mobile);

    UserProfile createUserProfile(CreateUserDto userDto);

    UserProfile saveUserProfile(UserProfile userProfile);
}
