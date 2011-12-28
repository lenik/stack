package com.bee32.plover.orm.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

/**
 * Declare the DTO type for the entity class.
 */
@Deprecated
@Retention(RUNTIME)
public @interface TransferBy {

    Class<? extends EntityDto<?, ?>> value();

}
