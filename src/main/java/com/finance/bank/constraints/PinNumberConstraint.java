package com.finance.bank.constraints;

import com.finance.bank.constraints.validators.PinNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PinNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER} )
@Retention(RetentionPolicy.RUNTIME)
public @interface PinNumberConstraint {
    String message() default "Invalid Pin code";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
