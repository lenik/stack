package com.bee32.plover.orm.validation;

import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 实体或DTO对象的 id 不能为 <code>null</code>。
 */
@Documented
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotNullIdValidator.class)
public @interface NotNullId {

    String message() default "{com.bee32.plover.orm.validation.NotNullId}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] nullIdValues() default {};

    boolean trim() default true;

}
