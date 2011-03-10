package com.bee32.plover.orm.util;

import com.bee32.plover.orm.unit.PersistenceUnit;
import com.bee32.plover.orm.unit.PersistenceUnits;

public class PUSelection {

    public static PersistenceUnit[] persistenceUnits;

    public static PersistenceUnit[] getPersistenceUnits() {
        if (persistenceUnits == null) {
            persistenceUnits = new PersistenceUnit[] { //
            PersistenceUnits.getInstance(), // The globle PU
            };
        }
        return persistenceUnits;
    }

    public static void setPersistenceUnits(PersistenceUnit... units) {
        persistenceUnits = units;
    }

}
