package com.bee32.plover.faces.view;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import com.bee32.plover.inject.spring.ScopeName;

@Scope
@ScopeName("view")
@Inherited
@Retention(RUNTIME)
@Documented
public @interface PerView {
}
