package com.bee32.plover.orm.unit;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;

/**
 * UseUnit annotated on the user class is inheritable.
 */
@Inherited
@Retention(RUNTIME)
@Documented
@Qualifier
public @interface Using {

    Class<? extends PersistenceUnit> value();

}
