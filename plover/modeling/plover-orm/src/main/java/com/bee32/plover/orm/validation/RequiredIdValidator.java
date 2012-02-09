package com.bee32.plover.orm.validation;

import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.model.validation.PloverConstraintValidator;

public class RequiredIdValidator
        extends PloverConstraintValidator<RequiredId, Object> {

    @Override
    public void initialize(RequiredId constraintAnnotation) {
    }

    @Override
    protected boolean validate(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        if (value instanceof Number) {
            long n = ((Number) value).longValue();
            if (n <= 0)
                return false;
        } else if (value instanceof String) {
            if (((String) value).trim().isEmpty())
                return false;
        }
        return true;
    }

}