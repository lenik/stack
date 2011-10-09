package com.bee32.plover.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.util.TextUtil;

public class NormalizedSizeValidator
        implements ConstraintValidator<Size, String> {

    private int min;
    private int max;
    private boolean normalize;

    public void initialize(Size parameters) {
        min = parameters.min();
        max = parameters.max();
        normalize = parameters.normalize();
        validateParameters();
    }

    /**
     * Checks the length of the specified string.
     *
     * @param s
     *            The string to validate.
     * @param constraintValidatorContext
     *            context in which the constraint is evaluated.
     *
     * @return Returns <code>true</code> if the string is <code>null</code> or the length of
     *         <code>s</code> between the specified <code>min</code> and <code>max</code> values
     *         (inclusive), <code>false</code> otherwise.
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null)
            return true;

        if (normalize)
            s = TextUtil.normalizeSpace(s);

        int length = s.length();
        return length >= min && length <= max;
    }

    private void validateParameters() {
        if (min < 0) {
            throw new IllegalArgumentException("The min parameter cannot be negative.");
        }
        if (max < 0) {
            throw new IllegalArgumentException("The max parameter cannot be negative.");
        }
        if (max < min) {
            throw new IllegalArgumentException("The length cannot be negative.");
        }
    }

}