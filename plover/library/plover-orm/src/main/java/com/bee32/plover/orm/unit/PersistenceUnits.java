package com.bee32.plover.orm.unit;

import java.util.Map;
import java.util.TreeMap;

public class PersistenceUnits {

    private static Map<String, IPersistenceUnit> units;

    static {
        units = new TreeMap<String, IPersistenceUnit>();
    }

    public static IPersistenceUnit getPersistenceUnit(Class<? extends IPersistenceUnit> persistenceUnitClass) {
        String name = persistenceUnitClass.getSimpleName();

        IPersistenceUnit unit = units.get(name);
        if (unit == null) {

            // for (IPersistenceUnit ServiceLoader.load(persistenceUnitClass);
        }
        return unit;
    }

}
