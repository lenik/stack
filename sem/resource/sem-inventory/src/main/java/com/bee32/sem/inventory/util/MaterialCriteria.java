package com.bee32.sem.inventory.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.MatchMode;

import com.bee32.plover.criteria.hibernate.CriteriaComposite;
import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.ICriteriaElement;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;
import com.bee32.sem.inventory.entity.MaterialType;

public class MaterialCriteria
        extends CriteriaSpec {

    @LeftHand(Material.class)
    public static ICriteriaElement categoryLike(String pattern) {
        if (pattern == null || pattern.isEmpty())
            return null;
        else
            return compose(alias("category", "category"), likeIgnoreCase("category.label", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(Material.class)
    public static ICriteriaElement clike(String pattern) {
        if (pattern == null || pattern.isEmpty())
            return null;
        else
            return
// likeIgnoreCase("material.category.label", pattern, MatchMode.ANYWHERE);
            compose(alias("category", "materialCategory"),
                    likeIgnoreCase("materialCategory.label", pattern, MatchMode.ANYWHERE));
    }

    @LeftHand(Material.class)
    public static CriteriaElement categoryOf(Integer categoryId) {
        if (categoryId == null)
            return null;
        else
            return equals("category.id", categoryId);
    }

    @LeftHand(MaterialCategory.class)
    public static CriteriaElement materialType(MaterialType... materialTypes) {
        return materialType(Arrays.asList(materialTypes));
    }

    @LeftHand(MaterialCategory.class)
    public static CriteriaElement materialType(Collection<MaterialType> materialTypes) {
        if (materialTypes == null || materialTypes.isEmpty())
            return null;
        StringBuilder vals = new StringBuilder();
        for (MaterialType c : materialTypes) {
            if (vals.length() != 0)
                vals.append(", ");
            vals.append("'" + c.getValue() + "'");
        }
        return sqlRestriction("{alias}.category in (" + //
                "select id from material_category where material_type in (" + vals + "))");
    }

    @LeftHand(MaterialCategory.class)
    public static CriteriaElement categoryType(MaterialType... materialTypes) {
        return categoryType(Arrays.asList(materialTypes));
    }

    @LeftHand(MaterialCategory.class)
    public static CriteriaElement categoryType(Collection<MaterialType> materialTypes) {
        if (materialTypes == null || materialTypes.isEmpty())
            return null;
        List<Character> vals = new ArrayList<>(materialTypes.size());
        for (MaterialType c : materialTypes) {
            vals.add(c.getValue());
        }
        return in("_materialType", vals);
    }

    public static CriteriaElement modelSpecLike(String pattern, boolean ignoreCase) {
        if (pattern == null)
            return null;
        pattern = pattern.trim();
        if (pattern.isEmpty())
            return null;
        if (ignoreCase)
            return likeIgnoreCase("modelSpec", pattern, MatchMode.ANYWHERE);
        else
            return like("modelSpec", pattern, MatchMode.ANYWHERE);
    }

    public static ICriteriaElement gPartNum(int categoryId) {
        CriteriaElement isnull = isNull("item.part");
        CriteriaComposite composite = compose(alias("children", "item"), not(in("item", isnull)));
        return conj(equals("category.id", categoryId), composite);
    }

}
