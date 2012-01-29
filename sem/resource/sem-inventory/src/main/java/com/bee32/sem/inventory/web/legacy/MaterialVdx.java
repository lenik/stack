package com.bee32.sem.inventory.web.legacy;

import org.primefaces.component.dialog.Dialog;

import com.bee32.plover.orm.util.EntityViewBean;

public class MaterialVdx
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    static final String CATEGORY_TREE = "main:categoryTree";
    static final String PANEL_INFO = "main:searchInfo";
    static final String BUTTON_PRESEARCH = "main:preSearchBtn";

    static final String MATERIAL_DIALOG = "dialogForm:materialDialog";

    boolean editable;

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public Dialog getMaterialDialog() {
        return (Dialog) findComponent(MATERIAL_DIALOG);
    }

}
