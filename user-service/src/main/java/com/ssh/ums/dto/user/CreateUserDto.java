package com.ssh.ums.dto.user;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateUserDto {
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
}
