package com.bee32.plover.web.faces.test;

import org.springframework.context.annotation.Scope;

import com.bee32.plover.web.faces.view.ViewBean;

@Scope("view")
public class TestBean
        extends ViewBean {

    private static final long serialVersionUID = 1L;

    MonthEnum month = MonthEnum.FEB;
    int activePart = 0;
    Boolean triState;

    public MonthEnum getMonth() {
        return month;
    }

    public void setMonth(MonthEnum month) {
        this.month = month;
    }

    public int getActivePart() {
        return activePart;
    }

    public void setActivePart(int activePart) {
        this.activePart = activePart;
    }

    public void jump1() {
        activePart = 1;
    }

    public void jump2() {
        activePart = 2;
    }

    public Boolean getTriState() {
        return triState;
    }

    public void setTriState(Boolean triState) {
        this.triState = triState;
    }

}
