package com.bee32.sem.world.thing;

import com.bee32.plover.orm.ext.xp.EntityExtDao;
import com.bee32.plover.orm.ext.xp.XPool;

public class ThingDao<E extends Thing<X>, X extends XPool<?>>
        extends EntityExtDao<E, Long, X> {

}
