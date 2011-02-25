package com.bee32.plover.orm.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

import com.bee32.plover.arch.Component;

public class PersistenceUnit
        extends Component
        implements IPersistenceUnit {

    public static final String GLOBAL = "global";

    private List<Class<?>> classes = new ArrayList<Class<?>>();

    public PersistenceUnit() {
        super(GLOBAL);
    }

    public PersistenceUnit(String name) {
        super(name);
    }

    @Override
    public Collection<Class<?>> getClasses() {
        return classes;
    }

    public void addPersistedClass(Class<?> clazz) {
        classes.add(clazz);
    }

    public void removePersistedClass(Class<?> clazz) {
        classes.remove(clazz);
    }

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

}
