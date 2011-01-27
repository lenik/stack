package com.bee32.plover.model.qualifier;

import java.util.Comparator;

public class QualifierComparator
        implements Comparator<Qualifier<?>> {

    @Override
    public int compare(Qualifier<?> o1, Qualifier<?> o2) {
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
        if (o1t.isInstance(o2)) {
            @SuppressWarnings("unchecked")
            Comparable<Qualifier<?>> o1c = (Comparable<Qualifier<?>>) o1;
            return o1c.compareTo(o2);
        }

        Class<?> o2t = o2.getQualifierType();
        return o1t.getName().compareTo(o2t.getName());
    }

    private static final QualifierComparator instance = new QualifierComparator();

    public static QualifierComparator getInstance() {
        return instance;
    }

}
