package com.bee32.sem.test;

import java.util.List;

import com.bee32.plover.orm.util.EntityViewBean;

public class ChooseTestBean
        extends EntityViewBean {

    private static final long serialVersionUID = 1L;

    Object target;

    @Override
    public void setSelection(List<?> selection) {
        super.setSelection(selection);
        System.out.println("Set selection: " + selection);
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        if (target == null)
            System.out.println("Set target to null.");
        else
            System.out.println("Set target: " + target);
        this.target = target;
    }

}
