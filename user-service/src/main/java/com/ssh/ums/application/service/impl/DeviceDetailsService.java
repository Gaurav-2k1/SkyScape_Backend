package com.ssh.ums.application.service.impl;


import com.ssh.ums.application.service.interfaces.IDeviceDetailsService;
import com.ssh.ums.dto.auth.DeviceDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ua_parser.Client;
import ua_parser.Parser;

@Service
@RequiredArgsConstructor
public class DeviceDetailsService implements IDeviceDetailsService {

    private final Parser uaParser = new Parser();

    @Override
    public DeviceDetails extractDeviceDetails() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request == null) {
            throw new IllegalStateException("No active HTTP request found");
        }

        String ipAddress = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        Client client = uaParser.parse(userAgent);

        return DeviceDetails.builder()
                .ipAddress(ipAddress)
                .userAgent(userAgent)
                .deviceType(client.device.family) // Like iPhone, Samsung, etc.
                .os(client.os.family)              // Like Android, Windows
                .browser(client.userAgent.family)  // Like Chrome, Safari
                .build();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            return servletRequestAttributes.getRequest();
        }
        return null;
    }

    private String getClientIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress.split(",")[0];
    }
}

