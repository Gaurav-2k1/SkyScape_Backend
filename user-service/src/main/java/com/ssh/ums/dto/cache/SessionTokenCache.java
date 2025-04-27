package com.ssh.ums.dto.cache;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@Builder
@Data
public class SessionTokenCache implements Serializable {
    private String accessToken;
    private String refreshToken;
}
