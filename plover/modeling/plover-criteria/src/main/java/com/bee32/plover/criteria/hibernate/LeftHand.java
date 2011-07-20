package com.bee32.plover.criteria.hibernate;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * TODO Rename this to @LeftHand.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LeftHand {

    Class<?>[] value();

}
