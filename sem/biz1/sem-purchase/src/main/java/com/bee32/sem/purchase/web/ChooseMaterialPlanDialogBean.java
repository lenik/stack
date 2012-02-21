package com.bee32.sem.purchase.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.purchase.dto.MaterialPlanDto;
import com.bee32.sem.purchase.dto.MaterialPlanItemDto;
import com.bee32.sem.purchase.entity.MaterialPlan;

public class ChooseMaterialPlanDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialPlanDialogBean.class);

    String header = "选择物料计划";
    boolean pending = false;

    public ChooseMaterialPlanDialogBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (pending)
            elements.add(new IsNull("purchaseRequest.id"));
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    ListMBean<MaterialPlanItemDto> itemsMBean = ListMBean.fromEL(this, "openedObject.items", MaterialPlanItemDto.class);

    public ListMBean<MaterialPlanItemDto> getItemsMBean() {
        return itemsMBean;
    }

}
