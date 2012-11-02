package com.bee32.sem.attendance.util;

import org.hibernate.criterion.CriteriaSpecification;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.salary.entity.MonthSalary;

public class SalaryCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listSalaryByYearAndMonth(int yearMonth) {
        return new Equals("yearMonth", yearMonth);
    }

    @LeftHand(MonthSalary.class)
    public static ICriteriaElement correspondingTicket(Long ticketId) {
        return compose(alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                new Equals("ticket.id", ticketId));
    }

}
