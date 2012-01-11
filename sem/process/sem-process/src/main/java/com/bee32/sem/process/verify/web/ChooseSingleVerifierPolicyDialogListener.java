package com.bee32.sem.process.verify.web;

import java.util.List;

import com.bee32.plover.web.faces.controls2.AbstractChooseDialogListener;

public abstract class ChooseSingleVerifierPolicyDialogListener
        extends AbstractChooseDialogListener {

    ChooseSingleVerifierPolicyDialogBean dialogBean;

    @Override
    public void submit() {
        dialogBean = getBean(ChooseSingleVerifierPolicyDialogBean.class);
        List<?> selection = dialogBean.getSelection();
        setSelection(selection);
        select(selection);
    }

    protected abstract void select(List<?> selection);

}
