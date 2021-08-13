package com.finance.bank.constraints.validators;

import com.finance.bank.constraints.OnlyAlphaConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OnlyAlphaValidator implements ConstraintValidator<OnlyAlphaConstraint, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }

    @Override
    public void initialize(OnlyAlphaConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
