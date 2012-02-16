package com.bee32.plover.orm.validation;

import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.model.validation.PloverConstraintValidator;

public class RequiredIdValidator
        extends PloverConstraintValidator<RequiredId, Object> {

    boolean notEmpty;
    boolean notZero;

    @Override
    public void initialize(RequiredId constraintAnnotation) {
        notEmpty = !constraintAnnotation.empty();
        notZero = !constraintAnnotation.zero();
    }

    @Override
    protected boolean validate(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        if (notZero) {
            long n = ((Number) value).longValue();
            if (n <= 0)
                return false;
        }
        if (notEmpty) {
            if (((String) value).trim().isEmpty())
                return false;
        }
        return true;
    }

}