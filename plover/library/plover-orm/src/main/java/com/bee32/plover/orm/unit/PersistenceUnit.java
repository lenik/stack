package com.bee32.plover.orm.unit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bee32.plover.arch.Component;

public class PersistenceUnit
        extends Component
        implements IPersistenceUnit {

    private List<Class<?>> classes = new ArrayList<Class<?>>();

    public static final String defaultUnitName = "default";

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

    @Override
    public String[] getMappingResources() {
        int size = classes.size();
        String[] mappingResources = new String[size];
        for (int index = 0; index < size; index++) {
            String className = classes.get(index).getName();
            // foo.Bar -> foo/Bar.hbm.xml
            String hbmPath = className.replace('.', '/') + ".hbm.xml";
            mappingResources[index] = /* "classpath:" + */hbmPath;
        }
        return mappingResources;
    }

}
