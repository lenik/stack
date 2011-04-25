package com.bee32.plover.orm.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Retention;

import com.bee32.plover.orm.entity.Entity;

@Retention(RUNTIME)
public @interface TransferBy {

    Class<? extends EntityDto<? extends Entity<? extends Serializable>, ? extends Serializable>> value();

}
