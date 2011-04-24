package com.bee32.plover.orm.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.Serializable;
import java.lang.annotation.Retention;

import com.bee32.plover.orm.entity.EntityBean;
import com.bee32.plover.orm.entity.EntityDao;

@Retention(RUNTIME)
public @interface Accessors {

    Class<? extends EntityDao<? extends EntityBean<? extends Serializable>, ? extends Serializable>>[] value();

}
