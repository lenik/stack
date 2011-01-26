package com.bee32.plover.inject.util;

import javax.free.AbstractPreorder;
import javax.free.ClassInheritancePreorder;
import javax.free.PackageNamePreorder;

public class NameQualifiedClassPreorder
        extends AbstractPreorder<NameQualifiedClass> {

    private static final ClassInheritancePreorder classPreorder = ClassInheritancePreorder.getInstance();
    private static final PackageNamePreorder qualifierPreorder = PackageNamePreorder.getInstance();

    @Override
    public int compare(NameQualifiedClass nqc1, NameQualifiedClass nqc2) {
        Class<?> class1 = nqc1.getClazz();
        Class<?> class2 = nqc2.getClazz();

        int cmp = classPreorder.compare(class1, class2);

        if (cmp == 0) {
            String qualifier1 = nqc1.getQualifier();
            String qualifier2 = nqc2.getQualifier();
            cmp = qualifierPreorder.compare(qualifier1, qualifier2);
        }
        return cmp;
    }

    @Override
    public NameQualifiedClass getPreceding(NameQualifiedClass nqc) {
        if (nqc == null)
            return null;

        Class<?> clazz = nqc.getClazz();
        String qualifier = nqc.getQualifier();

        if (qualifier != null) {
            String precedingQualifier = qualifierPreorder.getPreceding(qualifier);
            return new NameQualifiedClass(clazz, precedingQualifier);
        }

        if (clazz != null) {
            Class<?> precedingClass = classPreorder.getPreceding(clazz);
            return new NameQualifiedClass(precedingClass, null);
        }

        return null;
    }

    @Override
    public int precompare(NameQualifiedClass nqc1, NameQualifiedClass nqc2) {
        if (nqc1 == null)
            return nqc2 == null ? EQUALS : GREATER_THAN;
        if (nqc2 == null)
            return LESS_THAN;

        Class<?> class1 = nqc1.getClazz();
        Class<?> class2 = nqc2.getClass();

        int cmp = classPreorder.precompare(class1, class2);
        if (cmp == EQUALS) {
            String qualifier1 = nqc1.getQualifier();
            String qualifier2 = nqc2.getQualifier();
            cmp = qualifierPreorder.precompare(qualifier1, qualifier2);
        }

        return cmp;
    }

    private static final NameQualifiedClassPreorder instance = new NameQualifiedClassPreorder();

    public static NameQualifiedClassPreorder getInstance() {
        return instance;
    }

}
