package com.bee32.plover.orm.ext.tree;

import java.io.Serializable;

import com.bee32.plover.orm.entity.EntityDao;

public class TreeEntityDao<E extends TreeEntity<K, T>, K extends Serializable, T extends TreeEntity<K, T>>
        extends EntityDao<E, K> {

}
