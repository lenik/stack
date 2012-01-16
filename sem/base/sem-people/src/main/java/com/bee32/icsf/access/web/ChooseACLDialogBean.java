package com.bee32.icsf.access.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseACLDialogBean
        extends SimpleEntityViewBean {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseACLDialogBean.class);

    String caption = "Please choose an ACL..."; // NLS: 选择用户或组

    public ChooseACLDialogBean() {
        super(ACL.class, ACLDto.class, 0);
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
