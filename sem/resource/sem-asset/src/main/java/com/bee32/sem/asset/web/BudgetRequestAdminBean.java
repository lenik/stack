package com.bee32.sem.asset.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.misc.ScrollEntityViewBean;

@ForEntity(BudgetRequest.class)
public class BudgetRequestAdminBean
        extends ScrollEntityViewBean {

    private static final long serialVersionUID = 1L;

    public BudgetRequestAdminBean() {
        super(BudgetRequest.class, BudgetRequestDto.class, 0);
    }

}
