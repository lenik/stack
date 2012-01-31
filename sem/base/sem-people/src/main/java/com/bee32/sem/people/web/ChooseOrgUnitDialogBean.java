package com.bee32.sem.people.web;

import java.util.List;

import javax.free.Nullables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.misc.SimpleTreeEntityViewBean;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.entity.OrgUnit;

public class ChooseOrgUnitDialogBean
        extends SimpleTreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseOrgUnitDialogBean.class);

    String header = "Please choose an organization unit..."; // NLS: 选择用户或组
    Integer orgId;

    public ChooseOrgUnitDialogBean() {
        super(OrgUnit.class, OrgUnitDto.class, 0);
    }

    @Override
    protected void composeBaseCriteriaElements(List<ICriteriaElement> elements) {
        if (orgId != null)
            elements.add(new Equals("org.id", orgId));
    }

    // Properties

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setOrgId(Integer orgId) {
        if (!Nullables.equals(this.orgId, orgId)) {
            this.orgId = orgId;
            refreshTree();
        }
    }

}
