package com.bee32.plover.inject.scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import com.bee32.plover.inject.spring.ScopeName;

@Scope
@ScopeName("tgroup")
@Inherited
@Retention(RUNTIME)
@Documented
public @interface PerThreadGroup {
}
