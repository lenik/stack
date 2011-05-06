package com.bee32.plover.orm.ext.ext;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityDso;

public abstract class EntityExtDso<E extends EntityExt<K>, K extends Serializable, Dto extends EntityExtDto<E, K>>
        extends EntityDso<E, K, Dto> {

}
