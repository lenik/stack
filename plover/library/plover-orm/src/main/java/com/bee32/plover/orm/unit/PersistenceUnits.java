package com.bee32.plover.orm.unit;

import java.util.Map;
import java.util.TreeMap;

import com.bee32.plover.arch.service.Service;

public class PersistenceUnits {

    private static Map<String, IPersistenceUnit> units;

    static {
        units = new TreeMap<String, IPersistenceUnit>();

        for (IPersistenceUnit unit : Service.getContributions(IPersistenceUnit.class)) {
            String unitName = unit.getName();
            IPersistenceUnit mergedUnit = units.get(unitName);
            if (unit == null)
                unit = mergedUnit;
        }
    }

    public static IPersistenceUnit getPersistenceUnit(String unitName) {
        IPersistenceUnit unit = units.get(unitName);

        return unit;
    }

}
