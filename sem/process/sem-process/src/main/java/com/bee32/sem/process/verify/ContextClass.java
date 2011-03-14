package com.bee32.sem.process.verify;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ContextClass {

    /**
     * 需要上下文的支持。
     *
     * @return 需要支持的上下文类。如果不需要支持应该返回 <code>{@link Object}.class</code>.
     */
    Class<?> value();

}
