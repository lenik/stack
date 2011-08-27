package com.bee32.plover.ox1.color;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>blue</b>
 * <p>
 * <i>description</i> — Is it simply a catalog-like description which classifies or 'labels' an
 * object? If users of a system are labeled based on the department of a company they work within
 * and this doesn't change the way the system behaves, this would be a description.
 */
@Stereotype(style = "u-blue")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Blue {

}
