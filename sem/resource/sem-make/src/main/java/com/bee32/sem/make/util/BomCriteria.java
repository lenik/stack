package com.bee32.sem.make.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.make.entity.Part;

public class BomCriteria
        extends CriteriaSpec {

    @LeftHand(Part.class)
    public static ICriteriaElement targetLabel(String pattern, boolean ignoreCase) {
        return compose(alias("target", "material"), //
                ignoreCase ? likeIgnoreCase("material.label", pattern, MatchMode.ANYWHERE) //
                        : like("material.label", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(Part.class)
    public static ICriteriaElement targetCategory(int materialCategoryId) {
        return compose(alias("target", "material"), //
                new Equals("material.category.id", materialCategoryId));
    }

    @LeftHand(Part.class)
    public static ICriteriaElement targetModelSpec(String pattern, boolean ignoreCase) {
        return compose(alias("target", "material"), //
                ignoreCase ? likeIgnoreCase("material.modelSpec", pattern, MatchMode.ANYWHERE) //
                        : like("material.modelSpec", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(Part.class)
    public static ICriteriaElement notObsolete() {
        return isNull("obsolete");
    }

    @LeftHand(Part.class)
    public static ICriteriaElement findPartByMaterial(Long materialId) {
        return new Equals("target.id", materialId);

    }

}
