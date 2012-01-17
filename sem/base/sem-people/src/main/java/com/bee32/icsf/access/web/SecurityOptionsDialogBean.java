package com.bee32.icsf.access.web;

import com.bee32.icsf.access.acl.ACLDto;
import com.bee32.plover.orm.util.EntityPeripheralBean;

public class SecurityOptionsDialogBean
        extends EntityPeripheralBean {

    private static final long serialVersionUID = 1L;

    String caption = "安全选项";
    ACLDto selectedACL;

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public ACLDto getSelectedACL() {
        return selectedACL;
    }

    public void setSelectedACL(ACLDto selectedACL) {
        this.selectedACL = selectedACL;
    }

    public void applyACL() {
    }

    public String getCurrentACLText() {
        if (selectedACL == null)
            return "(n/a)";
        else
            return selectedACL.getLabel();
    }

}
