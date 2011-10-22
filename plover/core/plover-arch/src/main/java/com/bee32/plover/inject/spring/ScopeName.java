package com.bee32.plover.inject.spring;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface ScopeName {

    String value();

}
