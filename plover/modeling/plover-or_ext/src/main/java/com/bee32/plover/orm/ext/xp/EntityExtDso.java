package com.bee32.plover.orm.ext.xp;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityDso;

public abstract class EntityExtDso<E extends EntityExt<K, X>, K extends Serializable, X extends XPool<?>>
        extends EntityDso<E, K> {

}
