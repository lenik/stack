package com.bee32.plover.model.qualifier;

import java.util.Comparator;

public class QualifierComparator
        implements Comparator<Qualifier<? extends Qualifier<?>>> {

    @SuppressWarnings({ "unchecked", "rawtypes" })
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
        Class<?> o2t = o2.getQualifierType();
        if (!o1t.equals(o2t)) {
            String n1 = o1t.getName();
            String n2 = o2t.getName();
            cmp = n1.compareTo(n2);
            if (cmp != 0)
                return cmp;
        }

        int p1 = o1.getPriority();
        int p2 = o2.getPriority();
        cmp = p1 - p2;
        if (cmp != 0)
            return cmp;

        // o1t == o2t.
        return ((Qualifier) o1).compareSpecific(o2);
    }

    private static final QualifierComparator instance = new QualifierComparator();

    public static QualifierComparator getInstance() {
        return instance;
    }

}
