package com.bee32.icsf.access.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.bee32.icsf.access.Permission;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Restricted {

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
