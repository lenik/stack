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
    String namePattern;

    public ChoosePrincipalDialogBean() {
        super(Principal.class, PrincipalDto.class, 0);
        // addSearchFragment("类型为", User.class);
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

    public String getNamePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void addNamePatternFragment() {
        addSearchFragment("名称含有 " + namePattern, CommonCriteria.namedLike(namePattern));
    }

}
