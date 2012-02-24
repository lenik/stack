package com.bee32.sem.asset.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.asset.util.AssetCriteria;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseBudgetRequestDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseBudgetRequestDialogBean.class);

    public ChooseBudgetRequestDialogBean() {
        super(BudgetRequest.class, BudgetRequestDto.class, 0, AssetCriteria.haveNoCorrespondingTicket());
    }

}
