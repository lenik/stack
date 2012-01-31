package com.bee32.sem.inventory.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.bee32.plover.criteria.hibernate.CriteriaElement;
import com.bee32.plover.criteria.hibernate.CriteriaSpec;
import com.bee32.plover.criteria.hibernate.LeftHand;
import com.bee32.sem.inventory.Classification;
import com.bee32.sem.inventory.entity.Material;
import com.bee32.sem.inventory.entity.MaterialCategory;

public class MaterialCriteria
        extends CriteriaSpec {

    @LeftHand(Material.class)
    public static CriteriaElement categoryOf(Integer categoryId) {
        if (categoryId == null)
            return null;
        else
            return equals("category.id", categoryId);
    }

    @LeftHand(MaterialCategory.class)
    public static CriteriaElement classified(Classification... classifications) {
        return classified(Arrays.asList(classifications));
    }

    @LeftHand(MaterialCategory.class)
    public static CriteriaElement classified(Collection<Classification> classifications) {
        if (classifications == null || classifications.isEmpty())
            return null;
        List<Character> vals = new ArrayList<>(classifications.size());
        for (Classification c : classifications) {
            vals.add(c.getValue());
        }
        return in("_classification", vals);
    }

}
