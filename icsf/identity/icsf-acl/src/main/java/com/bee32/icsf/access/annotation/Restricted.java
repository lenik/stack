package com.bee32.icsf.access.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Restricted {

    String name() default "";

    Class<?>[] require() default {};

}
