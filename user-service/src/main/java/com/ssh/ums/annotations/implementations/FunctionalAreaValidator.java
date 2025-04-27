package com.ssh.ums.annotations.implementations;

import com.ssh.ums.annotations.interfaces.ValidFunctionalArea;
import com.ssh.ums.application.enums.FunctionalAreaEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class FunctionalAreaValidator implements ConstraintValidator<ValidFunctionalArea, String> {
    private static final Set<String> VALID_FUNCTIONAL_AREAS = Stream.of(FunctionalAreaEnum.values())
            .map(e -> e.name().
                    toLowerCase()).
            collect(Collectors.toSet());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null values should be handled by @NotNull annotation if required
        if (value == null) {
            return true;
        }

        // Check if the value is in the list of valid functional areas
        return VALID_FUNCTIONAL_AREAS.contains(value.toLowerCase());
    }
}
