package com.bee32.sem.salary.web;

import java.util.List;

import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.IsNull;
import com.bee32.sem.misc.ChooseEntityDialogBean;
import com.bee32.sem.salary.dto.MonthSalaryDto;
import com.bee32.sem.salary.entity.MonthSalary;

public class ChooseMonthSalaryDialogBean
        extends ChooseEntityDialogBean {

    private static final long serialVersionUID = 1L;
    Boolean haveNoCorrespondingTicket = false;

    public ChooseMonthSalaryDialogBean() {
        super(MonthSalary.class, MonthSalaryDto.class, 0);
    }

    @Override
    protected void composeBaseRestrictions(List<ICriteriaElement> elements) {
        if (haveNoCorrespondingTicket) {
            elements.add(new IsNull("ticket"));
        }
    }

    public Boolean getHaveNoCorrespondingTicket() {
        return haveNoCorrespondingTicket;
    }

    public void setHaveNoCorrespondingTicket(Boolean haveNoCorrespondingTicket) {
        this.haveNoCorrespondingTicket = haveNoCorrespondingTicket;
    }

}
