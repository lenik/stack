package com.bee32.plover.model.qualifier;

import java.util.Iterator;

import javax.free.AbstractNonNullComparator;

public class QualifiedComparator
        extends AbstractNonNullComparator<IQualified> {

    private static final QualifierComparator qcmp = QualifierComparator.getInstance();

    @Override
    public int compareNonNull(IQualified o1, IQualified o2) {
        Iterator<Qualifier<?>> i1 = o1.getQualifiers().iterator();
        Iterator<Qualifier<?>> i2 = o2.getQualifiers().iterator();

        Qualifier<?> q1;
        Qualifier<?> q2;

        while (true) {
            boolean exist1 = i1.hasNext();
            boolean exist2 = i2.hasNext();

            if (!exist1) // empty-1
                return exist2 ? 1 : 0;
            if (!exist2) // empty-2, exist-1
                return -1;

            q1 = i1.next();
            q2 = i2.next();

            int cmp = qcmp.compare(q1, q2);
            if (cmp != 0)
                return cmp;
        }
    }

    private static final QualifiedComparator instance = new QualifiedComparator();

    public static QualifiedComparator getInstance() {
        return instance;
    }

}
