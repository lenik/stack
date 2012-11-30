package com.bee32.sem.track.util;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;

public class IssueCriteria
        extends CriteriaSpec {

    public static ICriteriaElement getIssueByState(char stateValue) {
        return equals("_state", stateValue);
    }

    public static ICriteriaElement getUniqueById(long id) {
        return equals("id", id);
    }
}
