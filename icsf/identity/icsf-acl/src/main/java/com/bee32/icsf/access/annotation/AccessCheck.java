package com.bee32.icsf.access.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bee32.icsf.access.resource.AccessPoint;

/**
 * 访问点标注
 *
 * 访问控制点标注
 * <p lang="en">
 * Checked access point.
 *
 * @see AccessCheckAdvice
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
public @interface AccessCheck {

    /**
     * The name of implicit defined access point.
     * <p>
     * If not specified, or specify an empty string (""), the default name (field name or method
     * name) is then choosed.
     *
     * @see AccessPoint
     */
    String name() default "";

    /**
     * (Optional) Instead of define an implicit
     */
    Class<? extends String>[] require() default {};

    /**
     * Only used on type annotation.
     *
     * This attribute is ignored if declared on methods.
     */
    boolean allMethods() default true;

}
