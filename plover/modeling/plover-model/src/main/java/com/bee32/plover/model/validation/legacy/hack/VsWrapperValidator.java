package com.bee32.plover.model.validation.legacy.hack;

import java.lang.annotation.Annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bee32.plover.model.validation.PloverConstraintValidator;

public class VsWrapperValidator<A extends Annotation, T>
        extends PloverConstraintValidator<A, T> {

    final ConstraintValidator<A, T> impl;

    public VsWrapperValidator(ConstraintValidator<A, T> impl) {
        if (impl == null)
            throw new NullPointerException("impl");
        this.impl = impl;
    }

    public VsWrapperValidator(Class<ConstraintValidator<?, ?>> implClass)
            throws ReflectiveOperationException {
        this(VsWrapperValidator.<A, T> create(implClass));
    }

    static <A extends Annotation, T> ConstraintValidator<A, T> create(Class<ConstraintValidator<?, ?>> type)
            throws ReflectiveOperationException {
        ConstraintValidator<?, ?> instance = type.newInstance();

        @SuppressWarnings("unchecked")
        ConstraintValidator<A, T> casted = (ConstraintValidator<A, T>) instance;

        return casted;
    }

    @Override
    public void initialize(A constraintAnnotation) {
        impl.initialize(constraintAnnotation);
    }

    @Override
    protected boolean validate(T value, ConstraintValidatorContext context) {
        boolean result = impl.isValid(value, context);
        return result;
    }

}
