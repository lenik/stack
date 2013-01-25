package com.bee32.plover.site.scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import com.bee32.plover.inject.spring.ScopeName;

/**
 * XXX 值得注意的一点： per-site 并不能取代 &#64;Lazy。
 *
 * 但不明白提前获取 per-site 实例的意义何在？
 */
@Scope
@ScopeName("site")
@Inherited
@Retention(RUNTIME)
@Documented
public @interface PerSite {

}
