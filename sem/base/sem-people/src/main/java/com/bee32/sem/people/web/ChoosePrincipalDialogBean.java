package com.bee32.sem.people.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bee32.icsf.principal.Principal;
import com.bee32.icsf.principal.PrincipalCriteria;
import com.bee32.icsf.principal.PrincipalDto;
import com.bee32.plover.web.faces.controls2.IDialogCallback;
import com.bee32.sem.misc.SimpleEntityViewBean;

public class ChoosePrincipalDialogBean
        extends SimpleEntityViewBean
        implements IDialogCallback {

    private static final long serialVersionUID = 1L;

    static Logger logger = LoggerFactory.getLogger(ChoosePrincipalDialogBean.class);

    String caption = "Please choose a principal..."; // NLS: 选择用户或组

    public ChoosePrincipalDialogBean() {
        super(Principal.class, PrincipalDto.class, 0);
        // addSearchFragment("类型为", User.class);
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

    @Override
    public void addNameRestriction() {
        addSearchFragment("名称含有 " + pattern, PrincipalCriteria.namedLike(pattern));
        pattern = null;
    }

    @Override
    public void addNameOrLabelRestriction() {
        addNameRestriction();
    }

}
