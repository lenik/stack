package com.bee32.sem.people.web;

import com.bee32.plover.orm.annotation.ForEntity;
import com.bee32.sem.misc.TreeEntityViewBean;
import com.bee32.sem.misc.UnmarshalMap;
import com.bee32.sem.people.dto.OrgUnitDto;
import com.bee32.sem.people.entity.OrgUnit;

@ForEntity(OrgUnit.class)
public class OrgUnitAdminBean
        extends TreeEntityViewBean {

    private static final long serialVersionUID = 1L;

    protected String stringFragment;


    public OrgUnitAdminBean() {
        super(OrgUnit.class, OrgUnitDto.class, 0);
    }

    @Override
    protected void postUpdate(UnmarshalMap uMap)
            throws Exception {
        super.postUpdate(uMap);
        BEAN(ChooseOrgUnitDialogBean.class).refreshTree();
    }

}
