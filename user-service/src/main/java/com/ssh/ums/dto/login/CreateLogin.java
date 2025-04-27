package com.ssh.ums.dto.login;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Builder
@Data
public class CreateLogin {
    private UUID twoReferenceId;
    private String authMethod;
    private String statusLookup;
    private String ipAddress;
    private String deviceInfo;

}
