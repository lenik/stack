package com.bee32.plover.inject.spring;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Scope;

@Scope
@ScopeName("prototype")
@Inherited
@Retention(RUNTIME)
@Documented
public @interface Prototype {

}
