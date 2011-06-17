package com.bee32.plover.orm.ext.color;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>yellow</b>
 * <p>
 * <i>roles</i> — Is it a way of participating in an activity (by either a person, place, or thing)?
 * Signing into a system as an administrator, which changes program behavior by requiring a password
 * that guest accounts do not, is one example.
 */
@Stereotype(style = "u-yellow")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Yellow {

}
