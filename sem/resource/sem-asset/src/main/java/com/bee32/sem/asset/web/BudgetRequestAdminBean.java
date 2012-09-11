package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.FundFlowDto;
import com.bee32.sem.asset.entity.FundFlow;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(FundFlow.class)
public class BudgetRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public BudgetRequestAdminBean() {
        super(FundFlow.class, FundFlowDto.class, 0);
    }

}
