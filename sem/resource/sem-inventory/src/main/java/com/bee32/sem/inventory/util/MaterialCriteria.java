package com.bee32.sem.inventory.util;

import org.zkoss.lang.Strings;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class MaterialCriteria
        extends CriteriaSpec {

    public static CriteriaElement categoryOf(int categoryId) {
        return equals("category.id", categoryId);
    }

    public static CriteriaElement labelLike(String name) {
        if (Strings.isEmpty(name))
            return null;
        return like("label", "%" + name + "%");
    }

}
