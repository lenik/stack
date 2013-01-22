package com.bee32.sem.make.util;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.Equals;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.make.entity.Part;

public class BomCriteria
        extends CriteriaSpec {


    /*<-- change begin*/
    @LeftHand(Part.class)
    public static ICriteriaElement targetCategoryOrNameLike(int materialCategoryId,
            String searchPattern) {
        if (materialCategoryId == -1)
            return compose(
                    alias("target", "material"),//
                    likeIgnoreCase("material.lable", searchPattern,
                            MatchMode.ANYWHERE));
        else
            return compose(
                    alias("target", "material"),
                    likeIgnoreCase("material.label", searchPattern,
                            MatchMode.ANYWHERE),
                    equals("material.category.id", materialCategoryId));
    }

    @LeftHand(Part.class)
    public static ICriteriaElement specLike(String pattern){
        return //compose(alias("target", "material"),//
                likeIgnoreCase("material.modelSpec", pattern, MatchMode.ANYWHERE);//);
    }

    @LeftHand(Part.class)
    public static ICriteriaElement nameOrLabelLike(String pattern){
        return
            likeIgnoreCase("material.label", pattern, MatchMode.ANYWHERE); //
        // or(
        // likeIgnoreCase("material.label", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(Part.class)
    public static ICriteriaElement targetDescriptionLike(String pattern){
        return
                likeIgnoreCase("material.description", pattern, MatchMode.ANYWHERE);
    }

    /*--->end change*/

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

    public static ICriteriaElement specParamsOf(Long qcSpecId) {
        return new Equals("parent.id", qcSpecId);
    }
}
