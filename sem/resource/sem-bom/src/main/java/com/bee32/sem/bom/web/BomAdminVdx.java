package com.bee32.sem.bom.web;

import com.bee32.sem.bom.dto.PartDto;
import com.bee32.sem.bom.entity.Part;
import com.bee32.sem.sandbox.MultiTabEntityVdx;

public abstract class BomAdminVdx
        extends MultiTabEntityVdx {

    private static final long serialVersionUID = 1L;

    protected static final int TAB_INDEX = 0;
    protected static final int TAB_FORM = 1;

    boolean editable;
    boolean editableComp;

    int currTabComp = 0;

    public BomAdminVdx() {
        super(Part.class, PartDto.class, 0);
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditableComp() {
        return editableComp;
    }

    public void setEditableComp(boolean editableComp) {
        this.editableComp = editableComp;
    }

    public int getCurrTabComp() {
        return currTabComp;
    }

    public void setCurrTabComp(int currTabComp) {
        this.currTabComp = currTabComp;
    }

}
