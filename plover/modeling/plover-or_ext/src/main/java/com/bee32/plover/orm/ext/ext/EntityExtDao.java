package com.bee32.plover.orm.ext.ext;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityDao;

public class EntityExtDao<E extends EntityExt<K>, K extends Serializable>
        extends EntityDao<E, K> {

}
