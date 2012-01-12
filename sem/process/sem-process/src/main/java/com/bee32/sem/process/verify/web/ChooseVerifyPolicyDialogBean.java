package com.bee32.sem.process.verify.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.misc.SimpleEntityViewBean;
import com.bee32.sem.process.verify.VerifyPolicy;
import com.bee32.sem.process.verify.dto.VerifyPolicyDto;

public class ChooseVerifyPolicyDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChooseVerifyPolicyDialogBean.class);

    String pattern;

    public ChooseVerifyPolicyDialogBean() {
        super(VerifyPolicy.class, VerifyPolicyDto.class, 0);
    }

    public void search() {
        setCriteriaElements(CommonCriteria.namedLike(pattern));
        refreshCount();
    }

    @Override
    public void submit() {
        logger.debug("Selected: " + getSelection());
    }

    @Override
    public void cancel() {
        logger.debug("Cancelled.");
    }

    // Properties

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
