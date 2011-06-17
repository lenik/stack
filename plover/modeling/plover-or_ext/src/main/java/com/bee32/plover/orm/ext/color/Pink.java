package com.bee32.plover.orm.ext.color;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>pink</b>
 * <p>
 * <i>moment-interval</i> — Does it represent a moment or interval of time? An example would be an
 * object that temporarily stores login information during the authentication process.
 *
 * @see http://en.wikipedia.org/wiki/UML_colors
 */
@Stereotype(style = "u-pink")
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Pink {

}
