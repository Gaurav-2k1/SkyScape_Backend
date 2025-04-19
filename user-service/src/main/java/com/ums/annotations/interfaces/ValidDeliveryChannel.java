package com.ums.annotations.interfaces;


import com.ums.annotations.implementations.DeliveryChannelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


/**
 * Annotation that validates if a field value is one of the allowed functional areas
 * defined in the system.
 */
@Documented
@Constraint(validatedBy = DeliveryChannelValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDeliveryChannel {
    String message() default "Invalid delivery channel provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
