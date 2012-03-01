package com.bee32.plover.orm.builtin;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class PloverConfCriteria
        extends CriteriaSpec {

    public static CriteriaElement selector(String section) {
        return equals("section", section);
    }

    public static CriteriaElement selector(String section, String key) {
        return and(//
                equals("section", section), //
                equals("key", key));
    }

}
