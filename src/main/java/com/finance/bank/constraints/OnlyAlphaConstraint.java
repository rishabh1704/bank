package com.finance.bank.constraints;

import com.finance.bank.constraints.validators.OnlyAlphaValidator;
import com.finance.bank.constraints.validators.PhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OnlyAlphaValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER} )
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyAlphaConstraint {
    String message() default "Only Alphabets are allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
