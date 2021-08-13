package com.finance.bank.constraints;

import com.finance.bank.constraints.validators.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER} )
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {
    String message() default "Invalid Phone number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
