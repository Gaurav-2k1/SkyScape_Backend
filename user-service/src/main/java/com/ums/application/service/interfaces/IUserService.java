package com.ums.application.service.interfaces;

import com.ums.domain.entity.user.UserProfile;
import com.ums.dto.user.CreateUserDto;

public interface IUserService {
    UserProfile getUserProfileFromEmailOrMobile(String email, String mobile);

    UserProfile checkUserProfileFromEmailOrMobile(String email, String mobile);

    UserProfile createUserProfile(CreateUserDto userDto);

    UserProfile saveUserProfile(UserProfile userProfile);
}
