package com.bee32.sem.track;

import com.bee32.plover.orm.unit.ImportUnit;
import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.sem.event.SEMEventUnit;
import com.bee32.sem.people.SEMPeopleUnit;

@ImportUnit({ SEMPeopleUnit.class, SEMEventUnit.class })
public class SEMTrackUnit
        extends PersistenceUnit {

    @Override
    protected void preamble() {
    }

}
