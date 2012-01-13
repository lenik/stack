package com.bee32.icsf.access.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.access.acl.ACL;
import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChooseACLDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseACLDialogBean.class);

    String caption = "Please choose an ACL..."; // NLS: 选择用户或组
    String labelPattern;

    public ChooseACLDialogBean() {
        super(ACL.class, ACLDto.class, 0);
    }

    @Override
    public void selected() {
        logger.debug("Selected: " + getSelection());
    }

    @Override
    public void cancel() {
        logger.debug("Cancelled.");
    }

    // Properties

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getLabelPattern() {
        return labelPattern;
    }

    public void setLabelPattern(String labelPattern) {
        this.labelPattern = labelPattern;
    }

    public void addLabelRestriction() {
        addSearchFragment("名称含有 " + labelPattern, CommonCriteria.namedLike(labelPattern));
    }

}
