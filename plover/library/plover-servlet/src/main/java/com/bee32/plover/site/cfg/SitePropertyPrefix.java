package com.bee32.plover.site.cfg;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

/**
 * The prefix of site properties.
 */
@Documented
@Inherited
@Retention(RUNTIME)
public @interface SitePropertyPrefix {

    /**
     * You don't need a trailing dot (.) here.
     */
    String value();

}
