package com.bee32.sem.world.monetary;

import java.util.Date;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class FxrCriteria
        extends CriteriaSpec {

    public static ICriteriaElement beforeThan(Date date, int limit) {
        return compose(lessOrEquals("date", date));
    }

}
