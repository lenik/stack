package com.bee32.plover.inject.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContextConfiguration {

    /**
     * @see org.springframework.test.context.ContextConfiguration#value()
     */
    String[] value() default {};

    /**
     * @see org.springframework.test.context.ContextConfiguration#locations()
     */
    String[] locations() default {};

    /**
     * @see org.springframework.test.context.ContextConfiguration#inheritLocations()
     */
    boolean inheritLocations() default true;

    /**
     * @see org.springframework.test.context.ContextConfiguration#loader()
     */
    // Class<? extends ContextLoader> loader() default ContextLoader.class;

}
