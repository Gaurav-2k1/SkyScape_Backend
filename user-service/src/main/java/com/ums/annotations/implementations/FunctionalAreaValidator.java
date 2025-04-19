package com.ums.annotations.implementations;

import com.ums.annotations.interfaces.ValidFunctionalArea;
import com.ums.application.constants.LookUpConstants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;


public class FunctionalAreaValidator implements ConstraintValidator<ValidFunctionalArea, String> {
    private static final List<String> VALID_FUNCTIONAL_AREAS = Arrays.asList(
            LookUpConstants.FUNCTIONAL_AREA_LOGIN,
            LookUpConstants.FUNCTIONAL_AREA_CHANGE_PASSWORD,
            LookUpConstants.FUNCTIONAL_AREA_RESET_PASSWORD
            // Add other valid functional areas as needed
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null values should be handled by @NotNull annotation if required
        if (value == null) {
            return true;
        }

        // Check if the value is in the list of valid functional areas
        return VALID_FUNCTIONAL_AREAS.contains(value);
    }
}
