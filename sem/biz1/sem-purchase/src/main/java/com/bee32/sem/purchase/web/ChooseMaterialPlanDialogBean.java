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

    String caption = "选择物料计划";

    public ChooseMaterialPlanDialogBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
