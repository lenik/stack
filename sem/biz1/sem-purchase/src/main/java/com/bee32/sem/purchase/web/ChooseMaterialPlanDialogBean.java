package com.bee32.sem.purchase.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.entity.MaterialPlan;

public class ChooseMaterialPlanDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialPlanDialogBean.class);

    String header = "选择物料计划";

    public ChooseMaterialPlanDialogBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

}
