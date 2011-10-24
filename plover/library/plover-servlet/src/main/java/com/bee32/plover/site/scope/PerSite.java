package com.bee32.plover.site.scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import com.bee32.plover.inject.spring.ScopeName;

@Scope
@ScopeName("site")
@Retention(RUNTIME)
@Documented
public @interface PerSite {

}
