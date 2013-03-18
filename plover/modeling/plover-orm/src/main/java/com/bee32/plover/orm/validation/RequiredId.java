package com.bee32.plover.orm.validation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RequiredIdValidator.class)
public @interface RequiredId {

    String message() default "{com.bee32.plover.orm.validation.RequiredId}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean emptyForNull() default false;

    boolean zeroForNull() default false;

}
