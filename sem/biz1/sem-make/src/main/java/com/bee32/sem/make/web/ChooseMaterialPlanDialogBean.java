package com.bee32.sem.make.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.frame.ui.ListMBean;
import com.bee32.sem.make.dto.MaterialPlanDto;
import com.bee32.sem.make.dto.MaterialPlanItemDto;
import com.bee32.sem.make.entity.MaterialPlan;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseMaterialPlanDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseMaterialPlanDialogBean.class);

    boolean pending = false;

    public ChooseMaterialPlanDialogBean() {
        super(MaterialPlan.class, MaterialPlanDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (pending)
            elements.add(new IsNull("purchaseRequest.id"));
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
