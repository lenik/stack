package com.bee32.icsf.access.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bee32.icsf.access.Permission;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface Checkpoint {

    /**
     * The permission name implicit defined.
     * <p>
     * If not specified, or specify an empty string (""), the default name (field name or method
     * name) is then choosed.
     */
    String name() default "";

    /**
     * Instead of define an implicit
     */
    Class<? extends Permission>[] require() default {};

    boolean allMethods() default true;

}
