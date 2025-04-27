package com.ssh.ums.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDetails {
    private String ipAddress;
    private String userAgent;
    private String deviceType;  // e.g., Mobile, Tablet, Desktop
    private String os;          // e.g., Android, Windows
    private String browser;     // e.g., Chrome, Safari
    private Double latitude;    // optional
    private Double longitude;   // optional
}
