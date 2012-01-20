package com.bee32.plover.model.validation.core.impl;

import java.util.Arrays;

import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.model.validation.PloverConstraintValidator;
import com.bee32.plover.model.validation.core.Alphabet;

public class AlphabetValidator
        extends PloverConstraintValidator<Alphabet, String> {

    char[] tabIndexed;
    String hint;

    @Override
    public void initialize(Alphabet constraintAnnotation) {
        tabIndexed = constraintAnnotation.tab().toCharArray();
        hint = constraintAnnotation.hint();
        Arrays.sort(tabIndexed);
    }

    @Override
    protected boolean validate(String value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        int len = value.length();
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            int cIndex = Arrays.binarySearch(tabIndexed, c);
            if (cIndex < 0)
                return false;
        }

        return true;
    }

}
