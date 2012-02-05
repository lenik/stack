package com.bee32.sem.asset.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.asset.dto.BudgetRequestDto;
import com.bee32.sem.asset.entity.BudgetRequest;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseBudgetRequestDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseBudgetRequestDialogBean.class);

    String header = "选择资金流业务单";

    public ChooseBudgetRequestDialogBean() {
        super(BudgetRequest.class, BudgetRequestDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
