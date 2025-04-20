package com.ssh.ums.annotations.interfaces;


import com.ssh.ums.annotations.implementations.FunctionalAreaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * Annotation that validates if a field value is one of the allowed functional areas
 * defined in the system.
 */
@Documented
@Constraint(validatedBy = FunctionalAreaValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFunctionalArea {
    String message() default "Invalid functional area provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
