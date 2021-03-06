package com.bee32.plover.inject.qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * @see TestPurpose
 */
@Deprecated
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface RunConfig {

    String value();

}
