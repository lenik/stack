package com.bee32.plover.orm.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Retention;

import com.bee32.plover.orm.entity.EntityBean;

@Retention(RUNTIME)
public @interface TransferBy {

    Class<? extends EntityDto<? extends EntityBean<? extends Serializable>, ? extends Serializable>> value();

}
