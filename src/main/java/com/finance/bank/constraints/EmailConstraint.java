package com.finance.bank.constraints;

import com.finance.bank.constraints.validators.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER} )
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {

    String message() default "Invalid Email text";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
