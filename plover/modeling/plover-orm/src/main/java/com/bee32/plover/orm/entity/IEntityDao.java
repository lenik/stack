package com.bee32.plover.orm.entity;

import java.io.Serializable;

public interface IEntityDao<E extends IEntity<K>, K extends Serializable>
        extends IEntityRepository<E, K> {

}
