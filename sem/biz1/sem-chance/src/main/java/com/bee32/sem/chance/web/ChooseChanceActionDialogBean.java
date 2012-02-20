package com.bee32.sem.chance.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.chance.dto.ChanceActionDto;
import com.bee32.sem.chance.entity.ChanceAction;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseChanceActionDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseChanceActionDialogBean.class);

    String header = "Please choose a party..."; // NLS: 选择用户或组
    boolean pending = true;

    public ChooseChanceActionDialogBean() {
        super(ChanceAction.class, ChanceActionDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (pending)
            elements.add(new IsNull("chance.id"));
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

}
