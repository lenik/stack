package com.bee32.icsf.access.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.sem.misc.ChooseEntityDialogBean;

public class ChooseACLDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseACLDialogBean.class);

    public ChooseACLDialogBean() {
        super(ACL.class, ACLDto.class, 0);
    }

}
