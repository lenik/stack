package com.bee32.sem.people.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.ox1.util.CommonCriteria;
import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChoosePrincipalDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePrincipalDialogBean.class);

    String caption = "Please choose a principal..."; // NLS: 选择用户或组
    String pattern;

    public ChoosePrincipalDialogBean() {
        super(Principal.class, PrincipalDto.class, 0);
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

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
