package com.bee32.sem.file.web;

import com.bee32.plover.web.faces.controls2.AbstractSelectionAdapter;
import com.bee32.sem.people.web.ChoosePrincipalDialogBean;

public abstract class ChooseUserFileTagDialogListener
        extends AbstractSelectionAdapter {

    public ChooseUserFileTagDialogListener() {
        super(ChoosePrincipalDialogBean.class);
    }

}
