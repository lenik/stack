package com.bee32.plover.orm.unit;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceUnits {

    public static final String GLOBAL = "global";

    private static Map<String, PersistenceUnit> units;

    static {
        units = new TreeMap<String, PersistenceUnit>();

        for (PersistenceUnit unit : ServiceLoader.load(PersistenceUnit.class)) {
            String name = unit.getName();
            units.put(name, unit);
        }
    }

    public static PersistenceUnit getInstance() {
        return getInstance(GLOBAL);
    }

    public static synchronized PersistenceUnit getInstance(String unitName) {
        PersistenceUnit unit = units.get(unitName);
        if (unit == null) {
            unit = new PersistenceUnit(unitName);
            units.put(unitName, unit);
        }
        return unit;
    }

    /**
     * XXX - There is no way to distinguish whether the app is service-based or spring-based.
     */
    static {
        // ServiceModuleLoader.getInstance();
    }

}
