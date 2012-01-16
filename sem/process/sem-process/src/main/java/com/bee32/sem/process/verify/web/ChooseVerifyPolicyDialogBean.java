package com.bee32.sem.process.verify.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class ChooseVerifyPolicyDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseVerifyPolicyDialogBean.class);

    String caption = "选择审核策略";

    public ChooseVerifyPolicyDialogBean() {
        super(VerifyPolicy.class, VerifyPolicyDto.class, 0);
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

}
