package com.bee32.sem.event.dao;

import com.bee32.plover.orm.entity.EntityDao;
import com.bee32.sem.event.entity.Event;

public class AbstractEventDao<E extends Event>
        extends EntityDao<E, Long> {

}
