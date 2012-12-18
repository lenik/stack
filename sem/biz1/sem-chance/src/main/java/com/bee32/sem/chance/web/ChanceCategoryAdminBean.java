package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ChanceCategoryDto;
import com.bee32.sem.chance.entity.ChanceCategory;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChanceCategoryAdminBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ChanceCategoryAdminBean() {
        super(ChanceCategory.class, ChanceCategoryDto.class, 0);
    }

}
