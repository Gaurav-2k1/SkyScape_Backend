package com.ssh.ums.annotations.implementations;

import com.ssh.ums.annotations.interfaces.ValidDeliveryChannel;
import com.ssh.ums.application.constants.LookUpConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;


public class DeliveryChannelValidator implements ConstraintValidator<ValidDeliveryChannel, String> {
    private static final List<String> VALID_DELIVERY_CHANNELS = Arrays.asList(
            LookUpConstants.OTP_DELIVERY_CHANNEL_BOTH,
            LookUpConstants.OTP_DELIVERY_CHANNEL_MOBILE,
            LookUpConstants.OTP_DELIVERY_CHANNEL_EMAIL
            // Add other valid functional areas as needed
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null values should be handled by @NotNull annotation if required
        if (value == null) {
            return true;
        }

        // Check if the value is in the list of valid functional areas
        return VALID_DELIVERY_CHANNELS.contains(value);
    }
}
