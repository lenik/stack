package com.bee32.plover.ox1.xp;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityDao;

public abstract class EntityExtDao<E extends EntityExt<K, X>, K extends Serializable, X extends XPool<?>>
        extends EntityDao<E, K> {

}
