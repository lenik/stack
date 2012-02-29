package com.bee32.plover.orm.util.dto;

import java.util.Date;

import javax.free.AbstractNonNullComparator;

import com.bee32.plover.orm.util.EntityDto;

public class LastModifiedComparator
        extends AbstractNonNullComparator<EntityDto<?, ?>> {

    @Override
    public int compareNonNull(EntityDto<?, ?> o1, EntityDto<?, ?> o2) {
        Date lastModified1 = o1.getLastModified();
        Date lastModified2 = o2.getLastModified();
        int cmp = lastModified1.compareTo(lastModified2);
        if (cmp != 0)
            return cmp;
        return -1;
    }

    public static final LastModifiedComparator INSTANCE = new LastModifiedComparator();

}
