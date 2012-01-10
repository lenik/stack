package com.bee32.sem.people.web;

import java.util.List;

import com.bee32.plover.web.faces.controls2.AbstractChooseDialogListener;

public abstract class ChoosePrincipalDialogListener
        extends AbstractChooseDialogListener {

    ChoosePrincipalDialogBean dialogBean;

    @Override
    public void submit() {
        dialogBean = getBean(ChoosePrincipalDialogBean.class);
        List<?> selection = dialogBean.getSelection();
        setSelection(selection);
        select(selection);
    }

    protected abstract void select(List<?> selection);

}
