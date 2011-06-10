package com.bee32.plover.orm.ext.basic;

import java.io.Serializable;

import com.bee32.plover.orm.entity.Entity;

public interface IPostUpdating<E extends Entity<K>, K extends Serializable> {

    void postUpdate(E entity);

}
