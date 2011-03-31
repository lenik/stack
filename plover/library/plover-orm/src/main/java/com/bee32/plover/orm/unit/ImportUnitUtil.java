package com.bee32.plover.orm.unit;

import javax.free.IllegalUsageException;

public class ImportUnitUtil {

    /**
     * @return <code>null</code> If no {@link ImportUnit} is declared on the class.
     */
    public static PUnit getImportUnit(Class<?> clazz) {

        ImportUnit importUnit = clazz.getAnnotation(ImportUnit.class);
        if (importUnit == null)
            return PUnit.getDefault();

        Class<? extends PUnit> unitClass = importUnit.value();
        assert unitClass != null;

        PUnit unit;
        try {
            unit = unitClass.newInstance();
        } catch (Exception e) {
            throw new IllegalUsageException("Failed to instantiate PUnit " + unitClass, e);
        }

        return unit;
    }

}
