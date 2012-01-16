package com.bee32.plover.orm.validation;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.model.validation.PloverConstraintValidator;
import com.bee32.plover.orm.entity.Entity;
import com.bee32.plover.orm.util.EntityDto;

public class NotNullIdValidator
        extends PloverConstraintValidator<NotNullId, Object> {

    private Set<String> nullIdValues;
    private boolean trim;

    @Override
    public void initialize(NotNullId constraintAnnotation) {
        String[] nullIdValues = constraintAnnotation.nullIdValues();
        if (nullIdValues.length == 0)
            this.nullIdValues = null;
        else {
            this.nullIdValues = new HashSet<String>();
            for (String value : nullIdValues)
                this.nullIdValues.add(value);
        }
        trim = constraintAnnotation.trim();
    }

    @Override
    protected boolean validate(Object value, ConstraintValidatorContext context) {
        Object id;

        if (value == null)
            return false;

        if (value instanceof EntityDto<?, ?>)
            id = ((EntityDto<?, ?>) value).getId();
        else if (value instanceof Entity<?>)
            id = ((Entity<?>) value).getId();
        else
            return true;

        if (id == null)
            return false;

        if (nullIdValues == null)
            return false;

        String stringId = String.valueOf(id);
        if (trim)
            stringId = stringId.trim();
        if (nullIdValues.contains(stringId))
            return false;

        return true;
    }

}
