package com.bee32.sem.people.web;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.people.dto.PartyRecordCategoryDto;
import com.bee32.sem.people.entity.PartyRecordCategory;

public class RecordCategoryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public RecordCategoryAdminBean() {
        super(PartyRecordCategory.class, PartyRecordCategoryDto.class, 0);
    }

}
