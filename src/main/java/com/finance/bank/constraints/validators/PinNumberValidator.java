package com.finance.bank.constraints.validators;

import com.finance.bank.constraints.PinNumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PinNumberValidator implements ConstraintValidator<PinNumberConstraint, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.matches("^\\d{6}$");
    }

    @Override
    public void initialize(PinNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
