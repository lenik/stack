package com.bee32.sem.people.web;

import com.bee32.sem.people.dto.OrgDto;
import com.bee32.sem.people.entity.Org;

public class ChooseOrgDialogBean
        extends ChoosePartyDialogBean {

    private static final long serialVersionUID = 1L;

    public ChooseOrgDialogBean() {
        super(Org.class, OrgDto.class, 0);
    }

}
