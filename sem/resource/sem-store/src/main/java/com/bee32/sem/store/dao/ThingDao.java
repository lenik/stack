package com.bee32.sem.store.dao;

import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.plover.orm.ext.xp.XPool;
import com.bee32.sem.store.entity.Thing;

public class ThingDao<E extends Thing<X>, X extends XPool<?>>
        extends EntityExtDao<E, Long, X> {

}
