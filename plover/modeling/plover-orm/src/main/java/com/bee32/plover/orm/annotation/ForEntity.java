package com.bee32.plover.orm.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.bee32.plover.orm.entity.Entity;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ForEntity {

    Class<? extends Entity<?>> value();

    TypeParameter[] parameters() default {};

}
