package com.bee32.plover.arch;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A meta-annotation declares target as a composite element.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@CompositeElement
public @interface CompositeElement {

}
