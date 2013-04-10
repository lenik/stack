package com.bee32.sem.people.web;

import java.util.List;

import javax.free.Nullables;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.criteria.hibernate.Alias;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.sem.misc.ChooseTreeEntityDialogBean;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.entity.OrgUnit;

public class ChooseOrgUnitDialogBean
        extends ChooseTreeEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseOrgUnitDialogBean.class);

    Integer orgId;

    public ChooseOrgUnitDialogBean() {
        super(OrgUnit.class, OrgUnitDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (orgId != null) {
            if (orgId == -1) {
                // 如果等于-1，则查找本公司内部
                elements.add(new Alias("org", "org"));
                elements.add(new Equals("org.employee", true));
            } else {
                // 如果前面选中了某个公司，则查找该公司中的部门
                elements.add(new Equals("org.id", orgId));
            }
        }
    }

    public void setOrgId(Integer orgId) {
        if (!Nullables.equals(this.orgId, orgId)) {
            this.orgId = orgId;
            refreshTree();
        }
    }

}
