package com.bee32.plover.web.faces.test;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class TestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    MonthEnum month = MonthEnum.FEB;

    public MonthEnum getMonth() {
        return month;
    }

    public void setMonth(MonthEnum month) {
        this.month = month;
    }

}
