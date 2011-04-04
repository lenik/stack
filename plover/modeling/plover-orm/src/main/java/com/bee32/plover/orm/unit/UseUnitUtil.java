package com.bee32.plover.orm.unit;

import javax.free.IllegalUsageException;

public class UseUnitUtil {

    /**
     * UseUnit is inherited using Annotation-Inheritance.
     *
     * @return <code>null</code> If no {@link UseUnit} is declared on the class.
     */
    public static PersistenceUnit getUseUnit(Class<?> clazz) {

        UseUnit useUnit = clazz.getAnnotation(UseUnit.class);
        if (useUnit == null)
            return PersistenceUnit.getDefault();

        Class<? extends PersistenceUnit> unitClass = useUnit.value();
        assert unitClass != null;

        PersistenceUnit unit;
        try {
            unit = unitClass.newInstance();
        } catch (Exception e) {
            throw new IllegalUsageException("Failed to instantiate PUnit " + unitClass, e);
        }

        return unit;
    }

}
