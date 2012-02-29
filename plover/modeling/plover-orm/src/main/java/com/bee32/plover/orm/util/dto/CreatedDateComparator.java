package com.bee32.plover.orm.util.dto;

import java.util.Date;

import javax.free.AbstractNonNullComparator;

import com.bee32.plover.orm.util.EntityDto;

public class CreatedDateComparator
        extends AbstractNonNullComparator<EntityDto<?, ?>> {

    @Override
    public int compareNonNull(EntityDto<?, ?> o1, EntityDto<?, ?> o2) {
        Date createdDate1 = o1.getCreatedDate();
        Date createdDate2 = o2.getCreatedDate();
        int cmp = createdDate1.compareTo(createdDate2);
        if (cmp != 0)
            return cmp;
        return -1;
    }

    public static final CreatedDateComparator INSTANCE = new CreatedDateComparator();

}
