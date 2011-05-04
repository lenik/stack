package com.bee32.plover.orm.ext.dict;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityDao;

public class DictEntityDao<E extends DictEntity<K>, K extends Serializable>
        extends EntityDao<E, K> {

}
