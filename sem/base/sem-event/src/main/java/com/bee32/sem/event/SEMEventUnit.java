package com.bee32.sem.event;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.event.entity.Activity;
import com.bee32.sem.event.entity.Event;
import com.bee32.sem.event.entity.EventPriority;
import com.bee32.sem.event.entity.EventState;
import com.bee32.sem.event.entity.Task;
import com.bee32.sem.mail.SEMMailUnit;

@ImportUnit(SEMMailUnit.class)
public class SEMEventUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
        add(Event.class);
        add(Activity.class);
        add(Task.class);
        add(EventPriority.class);
        add(EventState.class);
    }

}
