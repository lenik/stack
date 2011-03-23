package com.bee32.sem.frame;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bee32.plover.arch.CompositeElement;
import com.bee32.sem.frame.menu.MenuContribution;

/**
 * The &#64;Contribution annotation is a composite element, so member fields with this annotation
 * will got its appearance cascade loaded.
 * <p>
 * At the other side, &#64;Contribution annotation is processed by {@link MenuContribution}, so its
 * target element id is used.
 */
@CompositeElement
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Contribution {

    /**
     * Target element id or path.
     */
    String value();

}
