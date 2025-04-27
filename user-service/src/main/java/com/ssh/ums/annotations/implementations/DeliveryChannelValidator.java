package com.ssh.ums.annotations.implementations;

import com.ssh.ums.annotations.interfaces.ValidDeliveryChannel;
import com.ssh.ums.application.enums.DeliveryChannelEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DeliveryChannelValidator implements ConstraintValidator<ValidDeliveryChannel, String> {
    private static final Set<String> VALID_DELIVERY_CHANNELS = Stream.of(DeliveryChannelEnum.values())
            .map(e -> e.name().toLowerCase())
            .collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null values should be handled by @NotNull annotation if required
        if (value == null) {
            return true;
        }

        // Check if the value is in the list of valid functional areas
        return VALID_DELIVERY_CHANNELS.contains(value.toLowerCase());
    }
}
