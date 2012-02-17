package com.bee32.plover.orm.validation;

import javax.free.IllegalUsageException;
import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.model.validation.PloverConstraintValidator;

public class RequiredIdValidator
        extends PloverConstraintValidator<RequiredId, Object> {

    boolean zeroForNull;
    boolean emptyForNull;

    @Override
    public void initialize(RequiredId constraintAnnotation) {
        zeroForNull = constraintAnnotation.zeroForNull();
        emptyForNull = constraintAnnotation.emptyForNull();
    }

    @Override
    protected boolean validate(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return false;
        if (zeroForNull) {
            if (!(value instanceof Number))
                throw new IllegalUsageException("Value is not number: " + value);
            long n = ((Number) value).longValue();
            if (n <= 0)
                return false;
        }
        if (emptyForNull) {
            if (!(value instanceof String))
                throw new IllegalUsageException("Value is not string: " + value);
            if (((String) value).trim().isEmpty())
                return false;
        }
        return true;
    }

}