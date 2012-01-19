package com.bee32.sem.inventory.util;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.entity.Material;

public class MaterialCriteria
        extends CriteriaSpec {

    @LeftHand(Material.class)
    public static CriteriaElement categoryOf(Integer categoryId) {
        if (categoryId == null)
            return null;
        else
            return equals("category.id", categoryId);
    }

}
