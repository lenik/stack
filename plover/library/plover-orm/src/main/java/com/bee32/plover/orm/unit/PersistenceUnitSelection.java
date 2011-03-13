package com.bee32.plover.orm.unit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.TreeMap;

import com.bee32.plover.arch.Component;
import com.bee32.plover.arch.ServiceModuleLoader;

public class PersistenceUnitSelection
        extends Component
        implements Iterable<PersistenceUnit> {

    private Map<String, PersistenceUnit> selectedUnits = new TreeMap<String, PersistenceUnit>();

    PersistenceUnitSelection() {
        super();
    }

    PersistenceUnitSelection(String name) {
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

    public synchronized void add(PersistenceUnit unit) {
        if (unit == null)
            throw new NullPointerException("unit");

        String unitName = unit.getName();
        PersistenceUnit existed = selectedUnits.get(unitName);
        if (existed != null)
            throw new IllegalStateException("P-Unit is already existed: " + unitName);

        selectedUnits.put(unitName, unit);
    }

    public void add(PersistenceUnit... units) {
        if (units == null)
            throw new NullPointerException("units");

        for (PersistenceUnit unit : units)
            add(unit);
    }

    public synchronized void remove(PersistenceUnit unit) {
        if (unit == null)
            throw new NullPointerException("unit");
        selectedUnits.remove(unit.getName());
    }

    public synchronized void remove(String unitName) {
        if (unitName == null)
            throw new NullPointerException("unitName");
        selectedUnits.remove(unitName);
    }

    @Override
    public Iterator<PersistenceUnit> iterator() {
        return selectedUnits.values().iterator();
    }

    public synchronized Collection<String> mergeMappingResources() {
        // Merge mapping resources
        Set<String> allResources = new LinkedHashSet<String>();
        for (IPersistenceUnit persistenceUnit : selectedUnits.values()) {
            if (persistenceUnit == null)
                throw new NullPointerException("persistenceUnit");

            String[] mappingResources = persistenceUnit.getMappingResources();

            for (String resource : mappingResources)
                allResources.add(resource);
        }

        return allResources;
    }

    public synchronized Collection<Class<?>> mergePersistentClasses() {
        // Merge mapping resources
        Set<Class<?>> allClasses = new LinkedHashSet<Class<?>>();
        for (IPersistenceUnit persistenceUnit : selectedUnits.values()) {
            if (persistenceUnit == null)
                throw new NullPointerException("persistenceUnit");

            Collection<Class<?>> classes = persistenceUnit.getClasses();

            for (Class<?> clazz : classes)
                allClasses.add(clazz);
        }

        return allClasses;
    }

    private static PersistenceUnitSelection serviceProviderSelection;

    public static PersistenceUnitSelection getServiceProviderSelection() {
        if (serviceProviderSelection == null) {
            synchronized (PersistenceUnitSelection.class) {
                if (serviceProviderSelection == null) {
                    serviceProviderSelection = new PersistenceUnitSelection();

                    // Load service modules
                    ServiceModuleLoader.getInstance();

                    for (PersistenceUnit unit : ServiceLoader.load(PersistenceUnit.class))
                        serviceProviderSelection.add(unit);

                }
            }
        }
        return serviceProviderSelection;
    }

    static Map<Object, PersistenceUnitSelection> contextLocal;
    static {
        contextLocal = new HashMap<Object, PersistenceUnitSelection>();
    }

    static final boolean shareAllContext = true;
    static PersistenceUnitSelection sharedSelection = new PersistenceUnitSelection("shared");

    public static synchronized PersistenceUnitSelection getContextSelection(Object contextKey) {
        if (shareAllContext) {

            return getSharedSelection();

        } else {

            PersistenceUnitSelection contextSelection = contextLocal.get(contextKey);

            if (contextSelection == null) {
                String contextName = String.valueOf(contextKey) + "-selection";
                contextSelection = new PersistenceUnitSelection(contextName);
                contextLocal.put(contextKey, contextSelection);
            }

            return contextSelection;
        }
    }

    public static PersistenceUnitSelection getSharedSelection() {
        return sharedSelection;
    }

}
