package com.bee32.sem.misc;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.Between;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class EntityCriteria
        extends CriteriaSpec {

    /**
     * Between the expanded date range.
     */
    public static CriteriaElement betweenEx(String property, Date beginDate, Date endDate) {
        return new Between(property, //
                LocalDateUtil.beginOfTheDay(beginDate), //
                LocalDateUtil.endOfTheDay(endDate));
    }

    public static CriteriaElement createdBetweenEx(Date beginDate, Date endDate) {
        return betweenEx("createdDate", beginDate, endDate);
    }

}
