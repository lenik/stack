package com.bee32.sem.attendance.util;

import java.util.Date;

import javax.free.Pair;

import org.hibernate.criterion.CriteriaSpecification;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.salary.entity.MonthSalary;
import com.bee32.sem.salary.util.SalaryDateUtil;

public class SalaryCriteria
        extends CriteriaSpec {

    public static ICriteriaElement listSalaryByYearAndMonth(int year, int month) {
        return new Equals("yearMonth", year * 100 + month);
    }

    public static ICriteriaElement listByDate(Date date) {
        Pair<Integer, Integer> yearAndMonth = SalaryDateUtil.getYearAndMonth(date);
        return new Equals("yearMonth", yearAndMonth.getFirst() * 100 + yearAndMonth.getSecond());
    }

    @LeftHand(MonthSalary.class)
    public static ICriteriaElement correspondingTicket(Long ticketId) {
        return compose(alias("ticket", "ticket", CriteriaSpecification.LEFT_JOIN), //
                new Equals("ticket.id", ticketId));
    }

}
