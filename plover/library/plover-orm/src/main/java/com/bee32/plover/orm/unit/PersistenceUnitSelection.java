package com.bee32.plover.orm.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.TreeMap;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.ServiceModuleLoader;

public class PersistenceUnitSelection
        extends Component
        implements Iterable<PersistenceUnit> {

    private Map<String, PersistenceUnit> selectedUnits = new TreeMap<String, PersistenceUnit>();

    public PersistenceUnitSelection() {
        super();
    }

    public PersistenceUnitSelection(String name) {
        super(name);
    }

    public synchronized PersistenceUnit getOrCreate(String unitName) {
        PersistenceUnit unit = selectedUnits.get(unitName);
        if (unit == null) {
            unit = new PersistenceUnit(unitName);
            selectedUnits.put(unitName, unit);
        }
        return unit;
    }

    public void add(PersistenceUnit unit) {
        if (unit == null)
            throw new NullPointerException("unit");

        String name = unit.getName();
        selectedUnits.put(name, unit);
    }

    public void add(PersistenceUnit... units) {
        if (units == null)
            throw new NullPointerException("units");

        for (PersistenceUnit unit : units)
            add(unit);
    }

    @Override
    public Iterator<PersistenceUnit> iterator() {
        return selectedUnits.values().iterator();
    }

    public List<String> mergeMappingResources() {
        // Merge mapping resources
        List<String> allResources = new ArrayList<String>();
        for (IPersistenceUnit persistenceUnit : selectedUnits.values()) {
            if (persistenceUnit == null)
                throw new NullPointerException("persistenceUnit");

            String[] mappingResources = persistenceUnit.getMappingResources();

            for (String resource : mappingResources)
                allResources.add(resource);
        }

        return allResources;
    }

    private static PersistenceUnitSelection serviceProviderSelection;

    public static PersistenceUnitSelection getServiceProviderSelection() {
        if (serviceProviderSelection == null)
            synchronized (PersistenceUnitSelection.class) {
                if (serviceProviderSelection == null) {
                    serviceProviderSelection = new PersistenceUnitSelection();

                    // Load service modules
                    ServiceModuleLoader.getInstance();

                    for (PersistenceUnit unit : ServiceLoader.load(PersistenceUnit.class))
                        serviceProviderSelection.add(unit);

                }
            }

        return serviceProviderSelection;
    }

    static Map<Object, PersistenceUnitSelection> contextLocal;
    static {
        contextLocal = new HashMap<Object, PersistenceUnitSelection>();
    }

    public static synchronized PersistenceUnitSelection getContextSelection(Object contextKey) {
        PersistenceUnitSelection selection = contextLocal.get(contextKey);

        if (selection == null) {
            String contextName = String.valueOf(contextKey) + "-selection";
            selection = new PersistenceUnitSelection(contextName);
            contextLocal.put(contextKey, selection);
        }

        return selection;
    }

}
