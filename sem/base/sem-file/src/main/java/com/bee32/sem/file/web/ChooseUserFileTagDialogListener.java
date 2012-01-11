package com.bee32.sem.file.web;

import java.util.List;

import com.bee32.plover.web.faces.controls2.AbstractChooseDialogListener;

public abstract class ChooseUserFileTagDialogListener
        extends AbstractChooseDialogListener {

    ChooseUserFileTagDialogBean dialogBean;

    @Override
    public void submit() {
        dialogBean = getBean(ChooseUserFileTagDialogBean.class);
        List<?> selection = dialogBean.getSelection();
        setSelection(selection);
        select(selection);
    }

    protected abstract void select(List<?> selection);

}
