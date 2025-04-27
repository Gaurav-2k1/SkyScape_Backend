package com.ssh.ums.application.utility;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;

public class OtpUtil {

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final Duration OTP_EXPIRY_DURATION = Duration.ofMinutes(5);

    // Characters used for alphanumeric OTP
    //testing


    private static final String NUMERIC = "0123456789";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateNumericOtp(int length) {
        return generateOtp(length, NUMERIC);
    }

    public static String generateAlphanumericOtp(int length) {
        return generateOtp(length, ALPHANUMERIC);
    }

    private static String generateOtp(int length, String characterSet) {
        if (length <= 0) throw new IllegalArgumentException("OTP length must be positive");

        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(characterSet.charAt(secureRandom.nextInt(characterSet.length())));
        }
        return otp.toString();
    }

    public static boolean isOtpExpired(Instant createdAt) {
        Instant now = Instant.now();
        return createdAt.plus(OTP_EXPIRY_DURATION).isBefore(now);
    }
}
