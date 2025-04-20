package com.ssh.ums.dto.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileTempCache implements Serializable {
    @Builder.Default
    private UUID referenceId = UUID.randomUUID();
    private String firstName;
    private String lastName;
    private String emailId;
    private String mobileNumber;
    @Builder.Default
    private Boolean emailVerified = Boolean.FALSE;
    @Builder.Default
    private Boolean mobileVerified = Boolean.FALSE;
    private UUID twoReferenceId;
    private String authMethod;
}
