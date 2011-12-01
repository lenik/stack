package com.bee32.sem.inventory.util;

import org.hibernate.criterion.MatchMode;
import org.zkoss.lang.Strings;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class MaterialCriteria
        extends CriteriaSpec {

    public static CriteriaElement categoryOf(int categoryId) {
        return equals("category.id", categoryId);
    }

    public static CriteriaElement labelLike(String label) {
        if (Strings.isEmpty(label))
            return null;
        return like("label", label, MatchMode.ANYWHERE);
    }

}
