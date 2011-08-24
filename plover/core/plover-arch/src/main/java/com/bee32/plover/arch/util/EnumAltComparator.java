package com.bee32.plover.arch.util;

import javax.free.AbstractNonNullComparator;
import javax.free.ComparableComparator;

public class EnumAltComparator<E extends EnumAlt<?, E>>
        extends AbstractNonNullComparator<E> {

    @Override
    public int compareNonNull(E o1, E o2) {
        Object v1 = o1.getValue();
        Object v2 = o2.getValue();
        int cmp = ComparableComparator.getRawInstance().compare(v1, v2);
        return cmp;
    }

}
