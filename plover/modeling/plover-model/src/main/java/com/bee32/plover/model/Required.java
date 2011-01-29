package com.bee32.plover.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a member element as required.
 * <p>
 * The plover's @Required is different to the Spring's, it's processed by stage renderers, rather
 * then APT preprocessors.
 *
 * @see org.springframework.beans.factory.annotation.Required
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
// @Deprecated
public @interface Required {

}
