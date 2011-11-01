package com.bee32.plover.inject.scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import org.springframework.context.annotation.ScopedProxyMode;

import com.bee32.plover.inject.spring.ScopeName;
import com.bee32.plover.inject.spring.ScopeProxy;

@Scope
@ScopeName("tgroup")
@ScopeProxy(ScopedProxyMode.INTERFACES)
@Inherited
@Retention(RUNTIME)
@Documented
public @interface PerThreadGroup {
}
