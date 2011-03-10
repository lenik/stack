package com.bee32.plover.model.qualifier;

import java.util.Comparator;

/**
 * Type-Priority > Qualifier-Type > Instance-Priority > Other*
 */
public class DerivedQualifierComparator
        implements Comparator<DerivedQualifier<? extends DerivedQualifier<?>>> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int compare(DerivedQualifier<?> o1, DerivedQualifier<?> o2) {
        if (o1 == null)
            return o2 == null ? 0 : -1;
        if (o2 == null)
            return 1;

        int qualifierPriority1 = o1.getQualifierPriority();
        int qualifierPriority2 = o2.getQualifierPriority();
        int cmp = qualifierPriority1 - qualifierPriority2;
        if (cmp != 0)
            return cmp;

        Class<?> o1t = o1.getQualifierType();
        Class<?> o2t = o2.getQualifierType();
        if (!o1t.equals(o2t)) {
            String n1 = o1t.getName();
            String n2 = o2t.getName();
            cmp = n1.compareTo(n2);
            if (cmp != 0)
                return cmp;
        }

        if (o1.kindOf(o2))
            return 1;
        if (o2.kindOf(o1))
            return -1;

        int p1 = o1.getPriority();
        int p2 = o2.getPriority();
        cmp = p1 - p2;
        if (cmp != 0)
            return cmp;

        // o1t == o2t.
        cmp = ((Qualifier) o1).compareSpecificTo(o2);
        if (cmp != 0)
            return cmp;

        if (o1.equals(o2))
            return 0;

        cmp = System.identityHashCode(o1) - System.identityHashCode(o2);
        return cmp;
    }

    private static final DerivedQualifierComparator instance = new DerivedQualifierComparator();

    public static DerivedQualifierComparator getInstance() {
        return instance;
    }

}
