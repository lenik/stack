package com.bee32.sem.event;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventCategory;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventStatus;
import com.bee32.sem.mail.SEMMailUnit;

/**
 * SEM 事件系统数据单元
 *
 * <p lang="en">
 * SEM Event Unit
 */
@ImportUnit(SEMMailUnit.class)
public class SEMEventUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Event.class);
        add(EventPriority.class);
        add(EventStatus.class);
        add(EventCategory.class);
    }

}
