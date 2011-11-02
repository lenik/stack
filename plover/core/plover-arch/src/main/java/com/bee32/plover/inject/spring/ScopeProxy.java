package com.bee32.plover.inject.spring;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

/**
 * This is the alternative for {@link Scope#proxyMode()}.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({
// ElementType.ANNOTATION_TYPE,
        ElementType.TYPE, ElementType.METHOD /* @Bean */})
public @interface ScopeProxy {

    /**
     * Specifies whether a component should be configured as a scoped proxy and if so, whether the
     * proxy should be interface-based or subclass-based.
     * <p>
     * Defaults to {@link ScopedProxyMode#NO}, indicating that no scoped proxy should be created.
     * <p>
     * Analogous to {@literal <aop:scoped-proxy/>} support in Spring XML.
     */
    ScopedProxyMode value() default ScopedProxyMode.TARGET_CLASS;

}
