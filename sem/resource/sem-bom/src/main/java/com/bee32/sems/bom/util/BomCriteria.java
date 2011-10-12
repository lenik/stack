package com.bee32.sems.bom.util;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.plover.criteria.hibernate.Like;
import com.bee32.sems.bom.entity.Part;

public class BomCriteria
        extends CriteriaSpec {

    @LeftHand(Part.class)
    public static ICriteriaElement findPartUseMaterialName(String pattern) {
        return compose(alias("target", "material"), //
                new Like("material.label", "%" + pattern + "%"));
    }

    @LeftHand(Part.class)
    public static ICriteriaElement listPartByCategory(int materialCategoryId) {
        return compose(alias("target", "material"), //
                new Equals("material.category.id", materialCategoryId));
    }

}
