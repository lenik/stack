package com.bee32.plover.model.validation;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public abstract class PloverConstraintValidator<A extends Annotation, T>
        implements ConstraintValidator<A, T> {

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        if (ValidationSwitcherManager.isValidationEnabled()) {
            return validate(value, context);
        } else {
            return true;
        }
    }

    protected abstract boolean validate(T value, ConstraintValidatorContext context);

}
