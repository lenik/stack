package com.bee32.plover.inject.cref;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * Import context config classes.
 */
@Inherited
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface Import {

    Class<?>[] value();

}
