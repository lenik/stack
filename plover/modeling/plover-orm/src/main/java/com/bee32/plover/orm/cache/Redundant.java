package com.bee32.plover.orm.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mark as redundant.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Redundant {

    int rank() default 0;

}
