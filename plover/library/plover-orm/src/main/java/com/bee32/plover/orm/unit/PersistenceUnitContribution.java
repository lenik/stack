package com.bee32.plover.orm.unit;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bee32.plover.arch.Component;

public class PersistenceUnitContribution
        extends Component
        implements IPersistenceUnitContribution {

    public PersistenceUnitContribution() {
        super();
    }

    public PersistenceUnitContribution(String name) {
        super(name);
    }

    Reference<List<Class<?>>> classesRef;

    /**
     * {@inheritDoc}
     * <p>
     * Get persistent classes from static Class fields.
     * <p>
     * Override this method to provide your own way to list the persistent classes.
     */
    @Override
    public Collection<Class<?>> getClasses() {
        List<Class<?>> classes = classesRef.get();
        if (classes == null)
            classes = findClasses();
        classesRef = new WeakReference<List<Class<?>>>(classes);
        return classes;
    }

    /**
     * Get persistent classes from static Class fields.
     */
    protected List<Class<?>> findClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        for (Field field : getClass().getFields()) {

            int modifiers = field.getModifiers();
            if (!Modifier.isStatic(modifiers))
                continue;

            if (field.getType() != Class.class)
                continue;

            Class<?> objectClass;
            try {
                objectClass = (Class<?>) field.get(null);
            } catch (IllegalAccessException e) {
                continue;
            }
            // String tableName = field.getName();

            list.add(objectClass);
        }
        return list;
    }

}
