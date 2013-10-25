package com.bee32.sem.chance.web;

import com.bee32.sem.chance.dto.ChanceStageDto;
import com.bee32.sem.chance.entity.ChanceStage;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChanceStageBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    public ChanceStageBean() {
        super(ChanceStage.class, ChanceStageDto.class, 0);
    }

}
