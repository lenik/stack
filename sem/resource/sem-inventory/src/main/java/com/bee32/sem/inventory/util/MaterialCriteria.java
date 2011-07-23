package com.bee32.sem.inventory.util;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;

public class MaterialCriteria
        extends CriteriaSpec {

    public static CriteriaElement categoryOf(long categoryId) {
        return equals("category.id", categoryId);
    }

    public static CriteriaElement nameLike(String name){
        return like("name", "%"+name+"%");
    }
}
